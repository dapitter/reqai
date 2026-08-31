export default function Home() {
  return (
    <main style={{ fontFamily: 'Arial, sans-serif', padding: 40, maxWidth: 1200, margin: '0 auto' }}>
      <header style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 48 }}>
        <div>
          <strong style={{ fontSize: 24 }}>ReqAI</strong>
          <div style={{ marginTop: 6, color: '#667085' }}>AI Requirements Engineering</div>
        </div>
        <button style={{ padding: '10px 16px', borderRadius: 8, border: '1px solid #D0D5DD', background: '#fff' }}>
          + Novo requisito
        </button>
      </header>

      <section>
        <h1 style={{ fontSize: 36, marginBottom: 10 }}>Bom dia.</h1>
        <p style={{ color: '#667085', marginBottom: 32 }}>Transforme demandas em requisitos claros com IA.</p>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: 16 }}>
          <Card title="Projetos" value="3" description="Projetos ativos" />
          <Card title="Requisitos" value="18" description="Em análise" />
          <Card title="Qualidade média" value="91%" description="Últimas análises" />
        </div>

        <section style={{ marginTop: 40, border: '1px solid #EAECF0', borderRadius: 12, padding: 24 }}>
          <h2 style={{ marginTop: 0 }}>Analisar um requisito</h2>
          <textarea
            placeholder="Descreva a demanda em linguagem natural..."
            style={{ width: '100%', minHeight: 140, border: '1px solid #D0D5DD', borderRadius: 8, padding: 14, boxSizing: 'border-box' }}
          />
          <button style={{ marginTop: 14, padding: '11px 18px', borderRadius: 8, border: 0, background: '#101828', color: '#fff' }}>
            Analisar com IA
          </button>
        </section>
      </section>
    </main>
  );
}

function Card({ title, value, description }: { title: string; value: string; description: string }) {
  return (
    <div style={{ border: '1px solid #EAECF0', borderRadius: 12, padding: 20 }}>
      <div style={{ color: '#667085', fontSize: 14 }}>{title}</div>
      <div style={{ fontSize: 30, fontWeight: 700, margin: '8px 0' }}>{value}</div>
      <div style={{ color: '#667085', fontSize: 14 }}>{description}</div>
    </div>
  );
}
