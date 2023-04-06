package com.automannn.springPractice.factoryLoader;

/**
 * @author chenkh
 * @time 2021/9/30 10:17
 */
public interface AutomannnExtensionAbility {
    void enhance();

    class AbilityOne implements AutomannnExtensionAbility{

        @Override
        public void enhance() {

        }
    }

    class AbilityTwo implements AutomannnExtensionAbility{

        @Override
        public void enhance() {

        }
    }

    class AbilityThree implements AutomannnExtensionAbility{

        @Override
        public void enhance() {

        }
    }
}
