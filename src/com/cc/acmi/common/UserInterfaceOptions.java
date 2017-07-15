package com.cc.acmi.common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import com.cc.acmi.presentation.painter.ambiente.AppDef2ColorPalette;
import com.cc.acmi.presentation.painter.ambiente.AppDefColorPalette;
import com.cc.framework.common.InvalidEnumType;
import com.cc.framework.ui.model.OptionListDataModel;
import com.cc.framework.ui.painter.ColorPalette;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.def.DefPainterFactory;
import com.cc.framework.ui.painter.def2.Def2PainterFactory;
import com.cc.framework.util.StringHelp;
/**
 * UserInterfaceOptions class is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.5 $
 */


public class UserInterfaceOptions
    implements OptionListDataModel, Serializable
{
    private static class UserInterfaceRegistration
    {

        private String label;
        private PainterFactory factory;
        private ColorPalette palette;

        public boolean equals(Object obj)
        {
            if(this == obj)
            {
                return true;
            }
            if(obj instanceof UserInterfaceRegistration)
            {
                return factory.equals(((UserInterfaceRegistration)obj).factory);
            }
            if(obj instanceof String)
            {
                return factory.getFactoryId().equals(obj);
            } else
            {
                return false;
            }
        }

        public PainterFactory getPainterFactory()
        {
            return factory;
        }

        public ColorPalette getColorPalette()
        {
            return palette;
        }

        public String getLabel()
        {
            return StringHelp.strcat(label, " (", getKey(), ")");
        }

        public String getKey()
        {
            return factory.getFactoryId();
        }

        public UserInterfaceRegistration(PainterFactory factory, ColorPalette palette, String label)
        {
            this.label = "";
            this.factory = null;
            this.palette = null;
            this.factory = factory;
            this.palette = palette;
            this.label = label;
        }
    }


    private static final long serialVersionUID = 0x52d5b2c47dbfdf38L;
    private static Vector factories = new Vector();

    public UserInterfaceOptions()
    {
    }

    public static void register(PainterFactory factory, ColorPalette palette, String label)
    {
        factories.add(new UserInterfaceRegistration(factory, palette, label));
    }

    public static PainterFactory getFactory(String id)
    {
        for(Iterator i = factories.iterator(); i.hasNext();)
        {
            UserInterfaceRegistration ui = (UserInterfaceRegistration)i.next();
            if(ui.equals(id))
            {
                return ui.getPainterFactory();
            }
        }

        throw new InvalidEnumType("Invalid UserInterface: " + id);
    }

    public static ColorPalette getPalette(String id)
    {
        for(Iterator i = factories.iterator(); i.hasNext();)
        {
            UserInterfaceRegistration ui = (UserInterfaceRegistration)i.next();
            if(ui.equals(id))
            {
                return ui.getColorPalette();
            }
        }

        throw new InvalidEnumType("Invalid UserInterface: " + id);
    }

    public Object getKey(int index)
    {
        return ((UserInterfaceRegistration)factories.get(index)).getKey();
    }

    public String getTooltip(int index)
    {
        return null;
    }

    public Object getValue(int index)
    {
        return ((UserInterfaceRegistration)factories.get(index)).getLabel();
    }

    public int size()
    {
        return factories.size();
    }

    static 
    {
        register(DefPainterFactory.instance(), new AppDefColorPalette(), "Default Painter");
        register(Def2PainterFactory.instance(), new AppDef2ColorPalette(), "Default Painter 2");
    }
}
