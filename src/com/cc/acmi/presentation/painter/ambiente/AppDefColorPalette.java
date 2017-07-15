package com.cc.acmi.presentation.painter.ambiente;

import com.cc.acmi.presentation.painter.AppColorPalette;
import com.cc.framework.ui.Color;

/**
 * AppDefColorPalette is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.3 $
 */
public class AppDefColorPalette extends AppColorPalette
{

    public AppDefColorPalette()
    {
        registerColor("app.color.bg.header", new Color("#97BCC3"));
        registerColor("app.color.bg.header.info", new Color("#B4CED4"));
        registerColor("app.color.bg.menu.main", new Color("#305A6C"));
        registerColor("app.color.bg.menu.tool", new Color("#307987"));
        registerColor("app.color.bg.sidebar", new Color("#C1D6DB"));
    }
}
