package org.evoting.cia.domain.indentity.agent;

import lombok.Data;
import org.evoting.cia.domain.device.Device;
import org.evoting.cia.domain.user.model.HumanUser;
import org.evoting.cia.domain.user.model.IUser;

@Data
public class Agent {

    private final IUser user;

    private final Device device;

    private final Agent originalAgent;

    private final Agent finalAgent;

    boolean isProxy;

    public Agent(
        HumanUser user,
        Device device,
        Agent originalAgent
    ) {
        this.user = user;
        this.device = device;
        this.originalAgent = originalAgent;
        this.isProxy = originalAgent != null;
        this.finalAgent = this.isProxy
            ? originalAgent.finalAgent
            : this;
    }
}
