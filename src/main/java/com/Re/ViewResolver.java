package com.Re;

import java.util.List;

/**
 * 视图解析器
 */
public class ViewResolver {

    private String prefix="WEB-INF/jsp/";

    private String suffix=".jsp";

    private boolean redirect=false;

    private String viewName;

    public ViewResolver resolverView(MyModelAndView myModelAndView){
        ViewResolver viewResolver = new ViewResolver();
        String[] str = myModelAndView.getView().split(":");
        if(str.length==2){
            viewResolver.setRedirect(true);
            viewResolver.setViewName(prefix+str[1]+suffix);
        }else{
            viewResolver.setViewName(prefix+str[0]+suffix);
        }

        return viewResolver;
    }

    @Override
    public String toString() {
        return "ViewResolver{" +
                "prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", redirect=" + redirect +
                ", viewName='" + viewName + '\'' +
                '}';
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void printList(List<String> list){
        list.forEach(item->{
            System.out.println(item);
        });
    }
}
