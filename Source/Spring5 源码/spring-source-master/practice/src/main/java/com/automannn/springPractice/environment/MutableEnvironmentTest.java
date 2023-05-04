package com.automannn.springPractice.environment;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenkh
 * @time 2021/8/12 17:07
 */
public class MutableEnvironmentTest {
    public static void main(String[] args) {
        StandardEnvironment environment  = new AutomannnEnvironment();
        System.out.println(environment.getSystemEnvironment());
        System.out.println(environment.getSystemProperties());

        System.out.println(environment.getProperty("application.name"));
        System.out.println(environment.getProperty("weather.today"));
        /*standardEnvironment是否customize，会影响该值的结果*/
        System.out.println(environment.getProperty("sun.jnu.encoding"));

    }

    static class AutomannnEnvironment extends StandardEnvironment{
        @Override
        protected void customizePropertySources(MutablePropertySources propertySources) {
            Map<String,Object> map = new HashMap<String, Object>(64);
            map.put("application.name","automannn");
            map.put("weather.today","sunny");
            MapPropertySource propertySource = new AutomannnMapPropertySource("automannn",map);
            propertySources.addLast(propertySource);
//            super.customizePropertySources(propertySources);
        }
    }

    static class AutomannnMapPropertySource extends MapPropertySource {

        public AutomannnMapPropertySource(String name, Map<String, Object> source) {
            super(name, source);
        }
    }
}
