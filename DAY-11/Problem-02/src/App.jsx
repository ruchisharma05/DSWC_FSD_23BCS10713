import { createContext, useContext, useEffect, useMemo, useState } from "react";

const ProtocolContext = createContext(null);

const initialPatients = [
  { id: "ER-100", name: "Riya Shah", condition: "Respiratory distress", riskScore: 82, bed: "A-12", etaMinutes: 4 },
  { id: "ER-101", name: "Aditya Menon", condition: "Compound fracture", riskScore: 55, bed: "B-03", etaMinutes: 12 },
  { id: "ER-102", name: "Sara Khan", condition: "Stroke alert", riskScore: 91, bed: "CT-02", etaMinutes: 2 }
];

const riskTickPattern = [2, -1, 3];

function calculateAggregateRisk(patients) {
  let total = 0;

  patients.forEach((patient) => {
    total += patient.riskScore;
  });

  for (let index = 0; index < 300000; index += 1) {
    total += 0;
  }

  return Math.round(total / patients.length);
}

function PatientCard({ patient }) {
  const { hospitalCode } = useContext(ProtocolContext);

  return (
    <article className="patient-card">
      <div>
        <p className="protocol-tag">{hospitalCode}</p>
        <h2>{patient.name}</h2>
        <p className="condition-copy">{patient.condition}</p>
      </div>
      <div className="patient-meta">
        <strong>{patient.riskScore}</strong>
        <span>Risk Score</span>
      </div>
      <div className="patient-meta">
        <strong>{patient.bed}</strong>
        <span>Assigned Bed</span>
      </div>
    </article>
  );
}

export default function App() {
  const [hospitalCode] = useState("MF-TRIAGE-09");
  const [patients, setPatients] = useState(initialPatients);
  const [nurseNotes, setNurseNotes] = useState("");

  useEffect(() => {
    let tick = 0;

    const intervalId = window.setInterval(() => {
      const delta = riskTickPattern[tick % riskTickPattern.length];
      tick += 1;

      setPatients((previousPatients) =>
        previousPatients.map((patient, patientIndex) => ({
          ...patient,
          riskScore: Math.max(25, Math.min(99, patient.riskScore + delta - patientIndex)),
          etaMinutes: Math.max(1, patient.etaMinutes - 1)
        }))
      );
    }, 3000);

    return () => {
      window.clearInterval(intervalId);
    };
  }, []);

  const aggregateRisk = useMemo(() => calculateAggregateRisk(patients), [patients]);

  return (
    <ProtocolContext.Provider value={{ hospitalCode }}>
      <main className="medflow-app">
        <section className="overview-card">
          <div>
            <p className="eyebrow">MedFlow</p>
            <h1>Hospital Triage Board</h1>
            <p className="overview-copy">
              Patient cards read the hospital code directly from context. Risk polling runs on an
              interval with cleanup, while the aggregate score ignores local nurse notes.
            </p>
          </div>
          <div className="risk-chip">
            <span>Aggregate ER Risk</span>
            <strong>{aggregateRisk}</strong>
          </div>
        </section>

        <section className="notes-card">
          <label htmlFor="nurse-notes">Charge Nurse Notes</label>
          <textarea
            id="nurse-notes"
            value={nurseNotes}
            onChange={(event) => setNurseNotes(event.target.value)}
            placeholder="Document staffing changes, trauma bay status, or escalation notes."
          />
        </section>

        <section className="patient-grid">
          {patients.map((patient) => (
            <PatientCard key={patient.id} patient={patient} />
          ))}
        </section>
      </main>
    </ProtocolContext.Provider>
  );
}
