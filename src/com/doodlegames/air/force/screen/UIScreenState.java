package com.doodlegames.air.force.screen;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.doodlegames.air.force.screen.BackProcessor;
import com.doodlegames.air.force.screen.UIScreen;
import com.doodlegames.air.force.utils.Settings;

import java.util.ArrayList;
import java.util.List;

public abstract class UIScreenState implements BackProcessor.BackFunction

{

    protected UIScreen screen;

    protected Skin skin;

    protected List<Action> stateAction;

    

    public UIScreenState(final UIScreen screen) {

        super();

        this.screen = screen;

        this.skin = screen.getSkin();

        this.stateAction = new ArrayList();

    }

    

    public void switchToThisState(final UIScreen screen) {

        if (screen == null) {

            Settings.e("screen should not be null in UIScreenState.switchToThisStateFunction");

            return;

        }

        this.screen = screen;

        this.skin = screen.getSkin();

        BackProcessor.setBackFuntion(this);

    }

    

    public void update(final float n) {

        if (this.stateAction.size() != 0) {

            final Action action = (Action)this.stateAction.get(0);

            if (action.act(n)) {

                this.stateAction.remove(action);

                return;

            }

        }

    }

}