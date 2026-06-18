import { useEffect, useState } from "react";

function DashboardPanel({ title, children }) {
  return (
    <section className="dashboard-panel">
      <div className="panel-header">
        <p className="panel-kicker">Mission Control</p>
        <h2>{title}</h2>
      </div>
      <div className="panel-body">{children}</div>
    </section>
  );
}

function TelemetrySubsystem({ fuelLevel, onAbortSequence }) {
  return (
    <DashboardPanel title="Telemetry Subsystem">
      <div className="telemetry-stack">
        <div className="metric-card">
          <span className="metric-label">Fuel Reserves</span>
          <strong>{fuelLevel}%</strong>
        </div>
        {fuelLevel < 20 && <p className="critical-warning">CRITICAL FUEL</p>}
        <button className="abort-button secondary" onClick={onAbortSequence}>
          Manual Abort
        </button>
      </div>
    </DashboardPanel>
  );
}

function LaunchCommander() {
  const [countdown, setCountdown] = useState(10);
  const [isAborted, setIsAborted] = useState(false);

  useEffect(() => {
    if (isAborted || countdown === 0) {
      return undefined;
    }

    const timerId = window.setInterval(() => {
      setCountdown((prevCountdown) => (prevCountdown > 0 ? prevCountdown - 1 : 0));
    }, 1000);

    return () => {
      window.clearInterval(timerId);
    };
  }, [countdown, isAborted]);

  const handleAbortSequence = () => {
    setIsAborted(true);
  };

  const fuelLevel = isAborted ? 0 : countdown * 10;
  const launchState = isAborted
    ? "ABORT SEQUENCE ENGAGED"
    : countdown === 0
      ? "LIFTOFF"
      : `T-${countdown}`;

  return (
    <div className="grid-shell">
      <DashboardPanel title="Launch Commander">
        <div className="launch-layout">
          <div>
            <p className="status-label">Launch Window</p>
            <h1 className={`countdown ${isAborted ? "aborted" : ""}`}>{launchState}</h1>
            <p className="status-copy">
              {isAborted
                ? "All orbital burn timers have been halted."
                : countdown === 0
                  ? "Main engines are green. Artemis is airborne."
                  : "Automated countdown is live. Closing this dashboard clears the timer."}
            </p>
          </div>
          <button className="abort-button" onClick={handleAbortSequence} disabled={isAborted}>
            ABORT
          </button>
        </div>
      </DashboardPanel>

      <TelemetrySubsystem fuelLevel={fuelLevel} onAbortSequence={handleAbortSequence} />

      <DashboardPanel title="Ground Systems">
        <div className="ground-grid">
          <div className="mini-card">
            <span>Life Support</span>
            <strong>{isAborted ? "Standby" : "Nominal"}</strong>
          </div>
          <div className="mini-card">
            <span>Nav Lock</span>
            <strong>{countdown <= 3 && !isAborted ? "Hard Lock" : "Tracking"}</strong>
          </div>
        </div>
      </DashboardPanel>
    </div>
  );
}

export default function App() {
  const [isDashboardOpen, setIsDashboardOpen] = useState(true);

  return (
    <main className="artemis-app">
      <div className="hero">
        <div>
          <p className="eyebrow">AeroSpace-X Mission Control</p>
          <h1>Artemis Orbital Launch Dashboard</h1>
          <p className="hero-copy">
            Mission operators can close the dashboard to shut down countdown activity or trigger
            an emergency abort from either command surface.
          </p>
        </div>
        <button className="mount-toggle" onClick={() => setIsDashboardOpen((prevOpen) => !prevOpen)}>
          {isDashboardOpen ? "Close Dashboard" : "Reopen Dashboard"}
        </button>
      </div>

      {isDashboardOpen ? (
        <LaunchCommander />
      ) : (
        <section className="shutdown-card">
          <h2>Dashboard Offline</h2>
          <p>All background countdown timers were cleaned up during unmount.</p>
        </section>
      )}
    </main>
  );
}
