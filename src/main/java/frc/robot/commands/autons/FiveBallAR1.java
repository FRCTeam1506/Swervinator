package frc.robot.commands.autons;

import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.RunPathPlannerTrajectory;
import frc.robot.commands.intake.ExtendAndIntake;
import frc.robot.commands.macros.ShootAndIndex;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDrivetrain;

public class FiveBallAR1 extends SequentialCommandGroup {

    public FiveBallAR1 (SwerveDrivetrain drivetrain, IntakeSubsystem intake, IndexerSubsystem indexer, ShooterSubsystem shooter, PathPlannerTrajectory trajectory1, PathPlannerTrajectory trajectory2, PathPlannerTrajectory trajectory3, PathPlannerTrajectory trajectory4) {
        
        addCommands(
            new PrintCommand("pick up 1, drive to 2, shoot"),
            new ParallelDeadlineGroup(
                new RunPathPlannerTrajectory(drivetrain, trajectory1),
                new ExtendAndIntake(intake)
            ).withTimeout(4.0),
            new ShootAndIndex(shooter, indexer, 1810.0).withTimeout(1.5)
        );

        addCommands(
            new PrintCommand("pick up 3, shoot"),
            new ParallelDeadlineGroup(
                new RunPathPlannerTrajectory(drivetrain, trajectory2),
                new ExtendAndIntake(intake)
            ).withTimeout(1.0),
            new ShootAndIndex(shooter, indexer, 1810.0).withTimeout(1.0)
        );

        addCommands(
            new PrintCommand("pick up 4, wait for 5"),
            new ParallelCommandGroup(
                new RunPathPlannerTrajectory(drivetrain, trajectory3),
                new ExtendAndIntake(intake)
            ).withTimeout(1.7 + 3.0),
            new InstantCommand(() -> intake.retract())
        );

        addCommands(
            new PrintCommand("drive back, shoot"),
            new RunPathPlannerTrajectory(drivetrain, trajectory4),
            new ShootAndIndex(shooter, indexer, 1810.0).withTimeout(1.5)
        );

    }
    
}