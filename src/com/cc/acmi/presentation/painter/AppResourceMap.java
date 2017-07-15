package com.cc.acmi.presentation.painter;

import com.cc.framework.ui.Color;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.painter.ResourceMapImp;

/**
 * AppResourceMap is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.3 $
 */

public class AppResourceMap extends ResourceMapImp
{

    private static final long serialVersionUID = 0xe819ccd4c484177eL;

    public AppResourceMap()
    {
        init();
    }

    protected void doRegisterColors()
    {
    }

    protected void doRegisterImages()
    {
        registerImage("app.tbtn.bg.left2", new ImageModelImp("app/images/buttons/btnBkg2_left.gif", 13, 25));
        registerImage("app.tbtn.bg.middle2", new ImageModelImp("app/images/buttons/btnBkg2_middle.gif", 160, 25));
        registerImage("app.tbtn.bg.right2", new ImageModelImp("app/images/buttons/btnBkg2_right.gif", 13, 25));
        registerImage("app.tbtn.bg.left3", new ImageModelImp("app/images/buttons/btnBkg3_left.gif", 7, 24));
        registerImage("app.tbtn.bg.middle3", new ImageModelImp("app/images/buttons/btnBkg3_middle.gif", 160, 24));
        registerImage("app.tbtn.bg.right3", new ImageModelImp("app/images/buttons/btnBkg3_right.gif", 7, 24));
        registerImage("app.tbtn.bg.left4", new ImageModelImp("app/images/buttons/btnBkg4_left.gif", 14, 27));
        registerImage("app.tbtn.bg.middle4", new ImageModelImp("app/images/buttons/btnBkg4_middle.gif", 120, 27));
        registerImage("app.tbtn.bg.right4", new ImageModelImp("app/images/buttons/btnBkg4_right.gif", 14, 27));
    }

    protected void doRegisterStrings()
    {
        registerString("app.file.css.default", "app/css/default.css");
    }

    public String getString(String resourceId, boolean returnNull)
    {
        String res = super.getString(resourceId, returnNull);
        if(res == null)
        {
            Color color = getColor(resourceId);
            if(color != null)
            {
                res = color.toHtml();
            }
        }
        return res;
    }
}
