import { useState } from "react";

function AssetRow({ asset, onBuy }) {
  const assetValue = asset.quantity * asset.price;

  return (
    <article className="asset-row">
      <div>
        <p className="asset-symbol">{asset.symbol}</p>
        <h2>{asset.name}</h2>
      </div>
      <div className="asset-metrics">
        <span>{asset.quantity.toFixed(4)} held</span>
        <strong>${assetValue.toLocaleString(undefined, { maximumFractionDigits: 2 })}</strong>
      </div>
      <div className="asset-actions">
        <button onClick={() => onBuy(asset.id, 0.05)}>Buy 0.05</button>
        <button onClick={() => onBuy(asset.id, 0.1)}>Buy 0.10</button>
      </div>
    </article>
  );
}

export default function App() {
  const [assets, setAssets] = useState([
    { id: "btc", symbol: "BTC", name: "Bitcoin", quantity: 0.65, price: 67240 },
    { id: "eth", symbol: "ETH", name: "Ethereum", quantity: 4.2, price: 3580 },
    { id: "sol", symbol: "SOL", name: "Solana", quantity: 31.5, price: 168 }
  ]);

  const handleBuyAsset = (assetId, amountToBuy) => {
    setAssets((previousAssets) =>
      previousAssets.map((asset) =>
        asset.id === assetId
          ? { ...asset, quantity: Number((asset.quantity + amountToBuy).toFixed(4)) }
          : asset
      )
    );
  };

  const totalPortfolioValue = assets.reduce(
    (runningTotal, asset) => runningTotal + asset.quantity * asset.price,
    0
  );

  return (
    <main className="crypto-app">
      <section className="overview-card">
        <div>
          <p className="eyebrow">CryptoWatch</p>
          <h1>Portfolio Tracker</h1>
          <p className="overview-copy">
            Holdings update immutably with <code>map()</code>, while the portfolio total is
            calculated live with <code>reduce()</code>.
          </p>
        </div>
        <div className="total-tile">
          <span>Total Portfolio Value</span>
          <strong>${totalPortfolioValue.toLocaleString(undefined, { maximumFractionDigits: 2 })}</strong>
        </div>
      </section>

      <section className="asset-list">
        {assets.map((asset) => (
          <AssetRow key={asset.id} asset={asset} onBuy={handleBuyAsset} />
        ))}
      </section>
    </main>
  );
}
