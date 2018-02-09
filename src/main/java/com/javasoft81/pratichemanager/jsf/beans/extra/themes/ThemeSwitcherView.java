/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft81.pratichemanager.jsf.beans.extra.themes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.themeswitcher.ThemeSwitcher;

/**
 *
 * @author iavazzo.andrea
 */
@ManagedBean(name = "themeSwitcherView")
@SessionScoped
public class ThemeSwitcherView implements Serializable {

    private Theme theme;

    /**
     * Creates a new instance of ThemeSwitcherView
     */
    public ThemeSwitcherView() {
    }

    private List<Theme> themes;

    @ManagedProperty("#{themeService}")
    private ThemeService service;
    private static Theme getStoredTheme(){
        FileInputStream streamIn = null;
        Theme st = null;
        try {
            streamIn = new FileInputStream("Theme.ser");
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            st = (Theme) objectinputstream.readObject();            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ThemeSwitcherView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(streamIn!=null)
                    streamIn.close();
            } catch (IOException ex) {
                Logger.getLogger(ThemeSwitcherView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return st;
    }
    @PostConstruct
    public void init() {
        themes = service.getThemes();
        theme = getStoredTheme();
        this.service.setApplicationTheme(theme);
        if (theme == null) {
            theme = themes.get(0);
        }
    }

    public Theme getThemeFromName(String name) {
        for (Theme t : this.themes) {
            if (t.getName().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setService(ThemeService service) {
        this.service = service;
    }

    public Theme getTheme() {
        return theme;
    }

    public void saveTheme(AjaxBehaviorEvent ajax) {
        FileOutputStream fout = null;
        try {
            this.service.setApplicationTheme((Theme) ((ThemeSwitcher) ajax.getSource()).getValue());
            fout = new FileOutputStream("Theme.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this.service.getApplicationTheme());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThemeSwitcherView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThemeSwitcherView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(ThemeSwitcherView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @FacesConverter(forClass = Theme.class)
    public static class ThemeSwitcherViewConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ThemeSwitcherView controller = (ThemeSwitcherView) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "themeSwitcherView");
            return controller.getThemeFromName(value);
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Theme) {
                Theme o = (Theme) object;
                return o.getName();
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Theme.class.getName()});
                return null;
            }
        }

    }
}
