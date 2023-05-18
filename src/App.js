import './App.css';
import Header from './components/Header';
import Comp1 from './components/Comp1';
import StatsGlobal from './components/StatsGlobal';
import Footer from './components/Footer';
import StatsUser from './components/StatsUser';
import { BrowserRouter as Router, Route, Routes, Redirect } from 'react-router-dom';


function App() {
  return (
    <Router>
      <div className="App text-white overflow-hidden">
        <Header />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/stats" element={<StatsUser />} />
        </Routes>
        <Footer />
      </div>
    </Router>
  );
}

function MainPage() {
  return (
    <div>
      <Comp1 />
      <StatsGlobal />
    </div>
  );
}

export default App;
