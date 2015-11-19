package se.lolektivet.linus.linuswars;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.RoundedRectangle;
import se.lolektivet.linus.linuswars.logic.enums.Direction;
import se.lolektivet.linus.linuswars.logic.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linus on 2014-09-20.
 */
public class GraphicalMenu {
   private static final int MARGIN = 6;
   private static final int ICON_SIZE = 16;

   private final Color _fillColor;
   private final Color _outlineColor;

   private final List<String> _textForItems;
   private final List<Image> _iconsForItems;
   private int _selectedItemIndex;

   private int _longestText;

   private Position _position;

   private Image _menuCursor;

   public GraphicalMenu(Image menuCursor) {
      _textForItems = new ArrayList<String>();
      _iconsForItems = new ArrayList<Image>();
      _position = new Position(0, 0);
      _longestText = 0;
      _fillColor = new Color(255, 204, 122);
      _outlineColor = new Color(0, 0, 0);
      _menuCursor = menuCursor;
      _selectedItemIndex = 0;
   }

   public void setPosition(Position position) {
      _position = position;
   }

   void addItem(String text) {
      addItem(text, null);
   }

   void addItem(String text, Image icon) {
      _textForItems.add(text);
      _iconsForItems.add(icon);
      if (text.length() > _longestText) {
         _longestText = text.length();
      }
   }

   void moveCursor(Direction direction) {
      if (direction.equals(Direction.DOWN)) {
         _selectedItemIndex += 1;
         _selectedItemIndex = _selectedItemIndex % _textForItems.size();
      } else if (direction.equals(Direction.UP)) {
         _selectedItemIndex -= 1;
         if (_selectedItemIndex < 0) {
            _selectedItemIndex += _textForItems.size();
         }
      }
   }

   String getTextForSelectedItem() {
      return _textForItems.get(_selectedItemIndex);
   }

   private int calculateHeight() {
      return 2*MARGIN + _iconsForItems.size() * ICON_SIZE;
   }

   private int calculateWidth() {
      return MARGIN*2 + ICON_SIZE + _longestText*5;
   }

   private RoundedRectangle getShape() {
      return new RoundedRectangle(_position.getX(), _position.getY(), calculateWidth(), calculateHeight(), 2);
   }

   void draw(Graphics graphics, Font font) {
      graphics.fill(getShape(), new GradientFill(0, 0, _fillColor, 0, 1, _fillColor));
      graphics.draw(getShape(), new GradientFill(0, 0, _outlineColor, 0, 1, _outlineColor));
      int topOfItemList = _position.getY() + MARGIN;
      int leftOfItemList = _position.getX() + MARGIN;
      for (int itemIndex = 0; itemIndex < _textForItems.size(); itemIndex++) {
         font.drawString(leftOfItemList, topOfItemList + itemIndex*ICON_SIZE, _textForItems.get(itemIndex));
      }
      _menuCursor.draw(_position.getX() + MARGIN - _menuCursor.getWidth(), topOfItemList + _selectedItemIndex*ICON_SIZE);
   }
}
