import { useEffect, useState } from "react";

export default function App() {
  const [isStealthMode, setIsStealthMode] = useState(false);
  const [lastPing, setLastPing] = useState(null);
  const [pingCount, setPingCount] = useState(0);

  useEffect(() => {
    if (isStealthMode) {
      return undefined;
    }

    const handleWindowClick = (event) => {
      const xPercent = (event.clientX / window.innerWidth) * 100;
      const yPercent = (event.clientY / window.innerHeight) * 100;

      setLastPing({
        x: event.clientX,
        y: event.clientY,
        xPercent,
        yPercent
      });
      setPingCount((previousCount) => previousCount + 1);
    };

    window.addEventListener("click", handleWindowClick);

    return () => {
      window.removeEventListener("click", handleWindowClick);
    };
  }, [isStealthMode]);

  return (
    <main className="sonar-app">
      <section className="command-bar">
        <div>
          <p className="eyebrow">SONAR</p>
          <h1>Submarine Ping Tracker</h1>
          <p className="command-copy">
            Global click pings are tracked through <code>window.addEventListener()</code>. Enabling
            stealth mode immediately removes the listener.
          </p>
        </div>
        <button
          className={isStealthMode ? "toggle stealth" : "toggle"}
          onClick={() => setIsStealthMode((previousMode) => !previousMode)}
        >
          {isStealthMode ? "Disable Stealth Mode" : "Enable Stealth Mode"}
        </button>
      </section>

      <section className="radar-shell">
        <div className="radar-screen">
          <div className="radar-ring ring-one" />
          <div className="radar-ring ring-two" />
          <div className="radar-ring ring-three" />
          <div className="sweep" />
          {lastPing && !isStealthMode && (
            <span
              className="ping-dot"
              style={{ left: `${lastPing.xPercent}%`, top: `${lastPing.yPercent}%` }}
            />
          )}
          {isStealthMode && <div className="stealth-overlay">STEALTH MODE</div>}
        </div>

        <aside className="telemetry-card">
          <div className="telemetry-item">
            <span>Tracking State</span>
            <strong>{isStealthMode ? "Silent Running" : "Active"}</strong>
          </div>
          <div className="telemetry-item">
            <span>Total Pings</span>
            <strong>{pingCount}</strong>
          </div>
          <div className="telemetry-item">
            <span>Last Coordinates</span>
            <strong>{lastPing ? `${lastPing.x}, ${lastPing.y}` : "Awaiting ping"}</strong>
          </div>
        </aside>
      </section>
    </main>
  );
}
