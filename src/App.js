import './App.css';
import Header from './components/Header';
import Comp1 from './components/Comp1';
import StatsGlobal from './components/StatsGlobal';
import Footer from './components/Footer';
import StatsUser from './components/StatsUser';
import { BrowserRouter as Router, Route, Routes, Redirect } from 'react-router-dom';
import TopTracks from './components/TopTracks';
import TopArtists from './components/TopArtists';
import TopGenres from './components/TopGenres';
import TrackHistory from './components/TrackHistory';
import FollowedArtists from './components/FollowedArtists';


function App() {
  return (
    <Router>
      <div className="App text-white overflow-hidden">
        <Header />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/stats" element={<StatsUser />} />
          <Route path="/stats/top-tracks" element={<TopTracks />} />
          <Route path="/stats/top-artists" element={<TopArtists />} />
          <Route path="/stats/top-genres" element={<TopGenres />} />
          <Route path="/stats/track-history" element={<TrackHistory />} />
          <Route path="/stats/followed-artists" element={<FollowedArtists />} />
          
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
