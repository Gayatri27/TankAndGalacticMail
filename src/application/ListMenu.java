package application;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListMenu {

  private ArrayList<SimpleMenuItem> menuItems;
  private int selectedIndex = 0;
  private Color textColor = Color.WHITE;
  private Color textColorSelected = Color.RED;

  private int fontSize = 50;
  private Font font = new Font("Monospaced", Font.PLAIN, fontSize);

  public ListMenu() {
    menuItems = new ArrayList<SimpleMenuItem>();

  }

  public void populate(JPanel panel) {

    for (SimpleMenuItem menuItem : menuItems) {
      menuItem.label = new JLabel(menuItem.displayText, JLabel.CENTER);
      menuItem.label.setForeground(textColor);
      menuItem.label.setFont(font);
      panel.add(menuItem.label);
    }

    if (!menuItems.get(selectedIndex).hasAction) next();

    reColor();

  }

  public void addMenuItem(String displayText, String functionName) {
    SimpleMenuItem menuItem = new SimpleMenuItem();

    menuItem.displayText = displayText;
    menuItem.functionName = functionName;

    if (menuItem.functionName == "") {
      menuItem.hasAction = false;
    }

    menuItems.add(menuItem);
  }

  public void next() {
    selectedIndex++;
    if (selectedIndex >= menuItems.size()) {
      selectedIndex = 0;
    }
    if (!menuItems.get(selectedIndex).hasAction) next();
    reColor();
  }

  public void previous() {
    selectedIndex--;
    if (selectedIndex <= -1) {
      selectedIndex = menuItems.size() - 1;
    }

    if (!menuItems.get(selectedIndex).hasAction) previous();

    reColor();
  }

  public void menuSelected(JPanel panel) {

    String functionName = menuItems.get(selectedIndex).functionName;

    try {
      panel.getClass().getMethod(functionName).invoke(panel);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

  private void reColor() {
    for (int i = 0; i < menuItems.size(); i++) {
      if (selectedIndex == i) {
        menuItems.get(i).label.setForeground(textColorSelected);
      } else {
        menuItems.get(i).label.setForeground(textColor);
      }
    }
  }


  private class SimpleMenuItem {
    String displayText;
    String functionName;
    JLabel label;
    boolean hasAction = true;
  }


}
