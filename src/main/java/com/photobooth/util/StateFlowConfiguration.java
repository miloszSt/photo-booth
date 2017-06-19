package com.photobooth.util;

import com.photobooth.model.StateDef;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "state-flow-config")
public class StateFlowConfiguration {

    private List<StateDef> states;

    @XmlElement(name = "state-def")
    public List<StateDef> getStates() {
        return states;
    }

    public void setStates(List<StateDef> states) {
        this.states = states;
    }
}
