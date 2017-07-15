package com.cc.acmi.presentation.painter;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.cc.framework.ui.control.Control;
import com.cc.framework.ui.painter.ColorPalette;
import com.cc.framework.ui.painter.ControlPainter;
import com.cc.framework.ui.painter.PainterContext;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.ResourceMap;

/**
 * AppPainterFactory is Common Controls  specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */
public class AppPainterFactory extends PainterFactory
{

    private static final long serialVersionUID = 0x36b8890be0ba4f9L;

    public AppPainterFactory(ColorPalette palette)
    {
        getResources().setColorPalette(palette);
    }

    protected ResourceMap createResourceMap()
    {
        return new AppResourceMap();
    }

    protected void doCreateHeaderIncludes(JspWriter writer)
        throws IOException
    {
        ResourceMap map = getResources();
        writer.print("\t<link rel='stylesheet' href='");
        writer.print(map.getString("app.file.css.default", false));
        writer.println("' charset='ISO-8859-1' type='text/css'>");
    }

    protected ControlPainter doCreatePainter(PainterContext painterContext, Control ctrl)
    {
        return null;
    }

    public String getFactoryId()
    {
        return "app";
    }
}
