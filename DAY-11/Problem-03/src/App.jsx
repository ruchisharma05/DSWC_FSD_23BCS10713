import { createContext, useContext, useEffect, useMemo, useState } from "react";

const RegionContext = createContext(null);

const catalog = {
  phone: [
    { id: "p-1", name: "Phone Case", relevance: 76, price: 24.99 },
    { id: "p-2", name: "Phone Stand", relevance: 64, price: 18.49 },
    { id: "p-3", name: "Phone Charger", relevance: 88, price: 39.99 }
  ],
  lap: [
    { id: "l-1", name: "Laptop Sleeve", relevance: 72, price: 32.99 },
    { id: "l-2", name: "Laptop Dock", relevance: 95, price: 149.99 },
    { id: "l-3", name: "Laptop Stand", relevance: 78, price: 59.99 }
  ],
  hea: [
    { id: "h-1", name: "Headphones", relevance: 91, price: 129.99 },
    { id: "h-2", name: "Headset Stand", relevance: 54, price: 21.99 },
    { id: "h-3", name: "Hearing Safe Earbuds", relevance: 67, price: 89.99 }
  ]
};

function fakeSuggestionFetch(searchTerm, signal) {
  return new Promise((resolve, reject) => {
    const requestTimer = window.setTimeout(() => {
      const key = searchTerm.trim().toLowerCase().slice(0, 3);
      const results = catalog[key] ?? [];
      resolve(results);
    }, 500 + searchTerm.length * 120);

    signal.addEventListener("abort", () => {
      window.clearTimeout(requestTimer);
      reject(new DOMException("Request aborted", "AbortError"));
    });
  });
}

function sortResultsByRelevance(results) {
  const clonedResults = [...results];
  clonedResults.sort((left, right) => right.relevance - left.relevance);

  for (let index = 0; index < 250000; index += 1) {
    clonedResults.length + 0;
  }

  return clonedResults;
}

function ResultItem({ result }) {
  const { region } = useContext(RegionContext);
  const currency = region === "US" ? "USD" : region === "EU" ? "EUR" : "GBP";

  return (
    <article className="result-item">
      <div>
        <h2>{result.name}</h2>
        <p>Relevance score: {result.relevance}</p>
      </div>
      <strong>
        {new Intl.NumberFormat("en-US", {
          style: "currency",
          currency,
          maximumFractionDigits: 2
        }).format(result.price)}
      </strong>
    </article>
  );
}

export default function App() {
  const [region, setRegion] = useState("US");
  const [searchTerm, setSearchTerm] = useState("phone");
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isFilterMenuOpen, setIsFilterMenuOpen] = useState(false);

  useEffect(() => {
    if (!searchTerm.trim()) {
      setResults([]);
      setIsLoading(false);
      return undefined;
    }

    const controller = new AbortController();
    setIsLoading(true);

    fakeSuggestionFetch(searchTerm, controller.signal)
      .then((nextResults) => {
        setResults(nextResults);
        setIsLoading(false);
      })
      .catch((error) => {
        if (error.name !== "AbortError") {
          setIsLoading(false);
        }
      });

    return () => {
      controller.abort();
    };
  }, [searchTerm]);

  const sortedResults = useMemo(() => sortResultsByRelevance(results), [results]);

  return (
    <RegionContext.Provider value={{ region }}>
      <main className="omni-app">
        <section className="search-shell">
          <div className="search-header">
            <div>
              <p className="eyebrow">OmniSearch</p>
              <h1>E-Commerce Engine</h1>
              <p className="search-copy">
                Region formatting is handled through context, stale suggestion requests are aborted,
                and expensive local relevance sorting is memoized.
              </p>
            </div>
            <div className="toolbar">
              <select value={region} onChange={(event) => setRegion(event.target.value)}>
                <option value="US">US</option>
                <option value="EU">EU</option>
                <option value="UK">UK</option>
              </select>
              <button onClick={() => setIsFilterMenuOpen((previousState) => !previousState)}>
                {isFilterMenuOpen ? "Hide Filters" : "Show Filters"}
              </button>
            </div>
          </div>

          {isFilterMenuOpen && (
            <div className="filter-drawer">
              <p>Filter drawer state does not trigger result sorting recomputation.</p>
            </div>
          )}

          <input
            className="search-input"
            value={searchTerm}
            onChange={(event) => setSearchTerm(event.target.value)}
            placeholder="Search products..."
          />

          <div className="results-shell">
            {isLoading ? <p className="status-line">Fetching suggestions...</p> : null}
            {!isLoading && sortedResults.length === 0 ? (
              <p className="status-line">No suggestions found for the current term.</p>
            ) : null}
            {sortedResults.map((result) => (
              <ResultItem key={result.id} result={result} />
            ))}
          </div>
        </section>
      </main>
    </RegionContext.Provider>
  );
}
