package com.cc.acmi.presentation.painter.ambiente;

import com.cc.acmi.presentation.painter.AppColorPalette;
import com.cc.framework.ui.Color;

/**
 * AppDef2ColorPalette is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.3 $
 */
public class AppDef2ColorPalette extends AppColorPalette
{

    public AppDef2ColorPalette()
    {
        registerColor("app.color.bg.header", new Color("#96B8D3"));
        registerColor("app.color.bg.header.info", new Color("#B8CBE1"));
        registerColor("app.color.bg.menu.main", new Color("#1E5C99"));
        registerColor("app.color.bg.menu.tool", new Color("#5A84B8"));
        registerColor("app.color.bg.sidebar", new Color("#CCDAEA"));
    }
}
