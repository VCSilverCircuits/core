package org.firstinspires.ftc.teamcode.vcsc.core.abstracts.task;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.PathChain;
import java.util.Collections;
import java.util.Set;

public class FollowPathTask implements Task {
  PathChain pathChain;
  Follower follower;
  boolean holdEnd = false;

  public FollowPathTask(Follower follower, PathChain pathChain, boolean holdEnd) {
    this.follower = follower;
    this.pathChain = pathChain;
    this.holdEnd = holdEnd;
  }

  public FollowPathTask(Follower follower, PathChain pathChain) {
    this(follower, pathChain, true);
  }

  @Override
  public boolean start() {
    System.out.println("[FollowPathTask::start] Following path chain.");
    follower.followPath(pathChain);
    return true;
  }

  @Override
  public void loop() {}

  private boolean followerRunningPathChain() {
    for (int i = 0; i < pathChain.size(); i++) {
      if (follower.getCurrentPath() == pathChain.getPath(i)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isFinished() {
    if (followerRunningPathChain()) {
      return !follower.isBusy();
    } else {
      return true;
    }
  }

  @Override
  public void cancel() {
    if (followerRunningPathChain()) {
      follower.breakFollowing();
    }
  }

  @Override
  public boolean isAsync() {
    return false;
  }

  @Override
  public boolean conflictsWith(Task other) {
    return false;
  }

  @Override
  public Set<Class<?>> requirements() {
    return Collections.emptySet();
  }
}
