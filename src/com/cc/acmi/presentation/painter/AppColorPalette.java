package com.cc.acmi.presentation.painter;

import java.util.Hashtable;

import com.cc.framework.ui.Color;
import com.cc.framework.ui.painter.ColorPalette;

/**
 * AppColorPalette is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public abstract class AppColorPalette
    implements ColorPalette
{

    private Hashtable colors;

    public AppColorPalette()
    {
        colors = new Hashtable();
    }

    public void registerColor(String resourceId, Color color)
    {
        colors.put(resourceId, color);
    }

    public Color getColor(String resourceId)
    {
        return (Color)colors.get(resourceId);
    }
}
