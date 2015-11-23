package se.lolektivet.linus.linuswars.graphics;

import org.newdawn.slick.*;
import se.lolektivet.linus.linuswars.logic.enums.Faction;
import se.lolektivet.linus.linuswars.logic.enums.TerrainTile;
import se.lolektivet.linus.linuswars.logic.enums.TerrainType;
import se.lolektivet.linus.linuswars.logic.enums.UnitType;

/**
 * Created by Linus on 2015-11-22.
 */
public class Sprites {
   private ResourceLoader _resourceLoader;
   private final Units _unitSpriteLoader;
   private final HpNumbers _hpNumbers;
   private final MovementArrowSprites _movementArrowSprites;
   private final Buildings _buildings;
   private final Terrain _terrain;

   public static Sprites initializeSprites() {
      Sprites sprites = new Sprites(new Units(), new HpNumbers(), new MovementArrowSprites(), new Buildings(), new Terrain());
      ResourceLoader resourceLoader = new ResourceLoader();
      sprites.init(resourceLoader);
      return sprites;
   }

   private Sprites(Units unitSpriteLoader, HpNumbers hpNumbers, MovementArrowSprites movementArrowSprites, Buildings buildings, Terrain terrain) {
      _unitSpriteLoader = unitSpriteLoader;
      _hpNumbers = hpNumbers;
      _movementArrowSprites = movementArrowSprites;
      _buildings = buildings;
      _terrain = terrain;
   }

   private void init(ResourceLoader resourceLoader) {
      _resourceLoader = resourceLoader;

      _unitSpriteLoader.init(_resourceLoader);
      _hpNumbers.init(_resourceLoader);
      _movementArrowSprites.init(_resourceLoader);
      _buildings.init(_resourceLoader);
      _terrain.init(_resourceLoader);
   }

   public Renderable getCursor() {
      return _resourceLoader.getCursorImage();
   }

   public Image getMenuCursor() {
      return _resourceLoader.getMenuCursorImage();
   }

   public Animation getAttackCursor() {
      Image attackCursorImage = _resourceLoader.getAttackCursorSheet();
      SpriteSheet attackCursorSheet = new SpriteSheet(attackCursorImage, 30, 29);
      return new Animation(attackCursorSheet, 500);
   }

   public Image getMoneyCounterPane() {
      return _resourceLoader.getMoneyCounterImage();
   }

   public Renderable getMovementArrowSection(MovementArrowSection arrowSection) {
      return _movementArrowSprites.getArrowSection(arrowSection);
   }

   public UnitSprite getUnitSprite(Faction unitFaction, UnitType unitType) {
      return _unitSpriteLoader.getUnitSprite(unitFaction, unitType);
   }

   public SpriteSheetFont getMainFont() {
      return new SpriteSheetFont(new SpriteSheet(_resourceLoader.getFontSpriteSheet(), 7, 14), ' ');
   }

   public Renderable getHpNumberImage(int nr) {
      return _hpNumbers.getHpNumberImage(nr);
   }

   public Renderable getBuildingSprite(TerrainType buildingType, Faction faction) {
      return _buildings.getBuildingSprite(buildingType, faction);
   }

   public Renderable getTerrainSprite(TerrainTile terrainTile) {
      return _terrain.getTerrainSprite(terrainTile);
   }
}
