package lk.ijse.dep.web;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppInitializer {

    private static AnnotationConfigApplicationContext ctx = buildApplicationContext();

    private static AnnotationConfigApplicationContext buildApplicationContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        return ctx;
    }

    public static AnnotationConfigApplicationContext getContext(){
        return ctx;
    }

}
