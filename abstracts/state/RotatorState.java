package vcsc.core.abstracts.state;

import vcsc.core.abstracts.action.Action;
import vcsc.core.abstracts.pose.RotatorPose;
import vcsc.teamcode.cmp.elbow.ElbowPose;

public class RotatorState<S extends RotatorState<S, P>, P extends RotatorPose> extends State<S> {
    double angle;
    P pose;

    public RotatorState() {
        super();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(Action<S> action, double angle) {
        assertLock(action);
        this.angle = angle;
        pose = null;
        notifyActuators();
    }

    public void setPose(Action<S> action, P pose) {
        assertLock(action);
        setAngle(action, pose.getAngle());
        this.pose = pose;
    }

    public P getPose() {
        return pose;
    }

}
