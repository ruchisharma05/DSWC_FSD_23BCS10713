import { createContext, useContext, useEffect, useMemo, useState } from "react";

const CurrencyContext = createContext(null);

const seedTransactions = [
  { id: "tx-101", asset: "BTC", quantity: 0.42, acquiredAt: "2026-06-12 08:30 UTC" },
  { id: "tx-102", asset: "ETH", quantity: 3.8, acquiredAt: "2026-06-12 10:05 UTC" },
  { id: "tx-103", asset: "SOL", quantity: 18, acquiredAt: "2026-06-12 11:50 UTC" },
  { id: "tx-104", asset: "BTC", quantity: 0.11, acquiredAt: "2026-06-12 13:40 UTC" }
];

const initialPrices = {
  BTC: { USD: 67200, EUR: 62000, GBP: 53100 },
  ETH: { USD: 3580, EUR: 3300, GBP: 2820 },
  SOL: { USD: 172, EUR: 158, GBP: 135 }
};

const priceAdjustments = [
  { BTC: { USD: 120, EUR: 110, GBP: 95 }, ETH: { USD: 12, EUR: 10, GBP: 8 }, SOL: { USD: 1.8, EUR: 1.5, GBP: 1.2 } },
  { BTC: { USD: -60, EUR: -58, GBP: -46 }, ETH: { USD: -8, EUR: -7, GBP: -6 }, SOL: { USD: 0.9, EUR: 0.7, GBP: 0.6 } },
  { BTC: { USD: 40, EUR: 35, GBP: 28 }, ETH: { USD: 5, EUR: 4, GBP: 3 }, SOL: { USD: -1.1, EUR: -0.8, GBP: -0.7 } }
];

const LivePriceFeed = {
  subscribe(listener) {
    let tick = 0;

    listener(initialPrices);

    const intervalId = window.setInterval(() => {
      tick += 1;
      const adjustment = priceAdjustments[tick % priceAdjustments.length];

      listener((previousPrices) => ({
        BTC: {
          USD: previousPrices.BTC.USD + adjustment.BTC.USD,
          EUR: previousPrices.BTC.EUR + adjustment.BTC.EUR,
          GBP: previousPrices.BTC.GBP + adjustment.BTC.GBP
        },
        ETH: {
          USD: previousPrices.ETH.USD + adjustment.ETH.USD,
          EUR: previousPrices.ETH.EUR + adjustment.ETH.EUR,
          GBP: previousPrices.ETH.GBP + adjustment.ETH.GBP
        },
        SOL: {
          USD: previousPrices.SOL.USD + adjustment.SOL.USD,
          EUR: previousPrices.SOL.EUR + adjustment.SOL.EUR,
          GBP: previousPrices.SOL.GBP + adjustment.SOL.GBP
        }
      }));
    }, 2500);

    return () => {
      window.clearInterval(intervalId);
    };
  }
};

function calculateMassivePortfolioValue(transactions, livePrices, selectedCurrency) {
  let totalValue = 0;

  transactions.forEach((transaction) => {
    const price = livePrices[transaction.asset]?.[selectedCurrency] ?? 0;
    totalValue += transaction.quantity * price;
  });

  for (let index = 0; index < 250000; index += 1) {
    totalValue += 0;
  }

  return totalValue;
}

function formatCurrency(amount, selectedCurrency) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: selectedCurrency,
    maximumFractionDigits: 2
  }).format(amount);
}

function CurrencySwitcher() {
  const { selectedCurrency, setCurrency } = useContext(CurrencyContext);

  return (
    <div className="switcher">
      {["USD", "EUR", "GBP"].map((currencyCode) => (
        <button
          key={currencyCode}
          className={selectedCurrency === currencyCode ? "switcher-button active" : "switcher-button"}
          onClick={() => setCurrency(currencyCode)}
        >
          {currencyCode}
        </button>
      ))}
    </div>
  );
}

function PortfolioMetrics({ transactions, livePrices }) {
  const { selectedCurrency } = useContext(CurrencyContext);

  const totalValue = useMemo(
    () => calculateMassivePortfolioValue(transactions, livePrices, selectedCurrency),
    [transactions, livePrices, selectedCurrency]
  );

  return (
    <section className="metrics-card">
      <p className="eyebrow">Portfolio Metrics</p>
      <h2>{formatCurrency(totalValue, selectedCurrency)}</h2>
      <p className="metrics-copy">
        The valuation is memoized and recomputed only when transactions, live prices, or the
        selected currency change.
      </p>
    </section>
  );
}

function TransactionHistory({ transactions, livePrices }) {
  const { selectedCurrency } = useContext(CurrencyContext);

  return (
    <section className="history-card">
      <div className="history-header">
        <div>
          <p className="eyebrow">Transaction History</p>
          <h2>Live Exposure</h2>
        </div>
        <CurrencySwitcher />
      </div>

      <div className="history-list">
        {transactions.map((transaction) => {
          const markPrice = livePrices[transaction.asset]?.[selectedCurrency] ?? 0;
          const currentValue = transaction.quantity * markPrice;

          return (
            <article key={transaction.id} className="history-row">
              <div>
                <strong>{transaction.asset}</strong>
                <p>{transaction.acquiredAt}</p>
              </div>
              <div>
                <span>{transaction.quantity} units</span>
                <strong>{formatCurrency(currentValue, selectedCurrency)}</strong>
              </div>
            </article>
          );
        })}
      </div>
    </section>
  );
}

export default function App() {
  const [selectedCurrency, setCurrency] = useState("USD");
  const [transactions] = useState(seedTransactions);
  const [livePrices, setLivePrices] = useState(initialPrices);

  useEffect(() => {
    const unsubscribe = LivePriceFeed.subscribe((priceUpdate) => {
      setLivePrices((previousPrices) =>
        typeof priceUpdate === "function" ? priceUpdate(previousPrices) : priceUpdate
      );
    });

    return () => {
      unsubscribe();
    };
  }, []);

  return (
    <CurrencyContext.Provider value={{ selectedCurrency, setCurrency }}>
      <main className="app-shell">
        <section className="hero-card">
          <div>
            <p className="eyebrow">CoinStream</p>
            <h1>Real-Time Portfolio Dashboard</h1>
            <p className="hero-copy">
              Currency selection is shared through context, live prices subscribe once on mount,
              and heavy portfolio valuation work is memoized.
            </p>
          </div>
          <PortfolioMetrics transactions={transactions} livePrices={livePrices} />
        </section>

        <TransactionHistory transactions={transactions} livePrices={livePrices} />
      </main>
    </CurrencyContext.Provider>
  );
}
