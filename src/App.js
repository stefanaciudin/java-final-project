import './App.css';
import Header from './components/Header';
import Comp1 from './components/Comp1';
import Stats from './components/Stats';
import Footer from './components/Footer';

function App() {
  return (
    <div className="App text-white overflow-hidden">
        <Header/>
        <Comp1/>
        <Stats/>
        <Footer/>
    </div>
  )
}

export default App;
