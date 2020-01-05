package pl.suzuyo.common.gui;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {

    public IconButton(Icon icon) {
        super(icon);
        Dimension preferredSize = getPreferredSize();
        int newSize = preferredSize.height;
        Dimension newDimension = new Dimension(newSize, newSize);
        setPreferredSize(newDimension);
        setMaximumSize(newDimension);
        setMinimumSize(newDimension);
    }
}
