import './App.css';
import routes from './routes';
import {
  BrowserRouter as Router,
  Route, Routes
} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {


  const getRoutes = () =>
    routes.map((route) => {
      return <Route exact path={route.route} element={route.component} key={route.key} />;
    });

  return (
    <Router>
      <Routes>
        {getRoutes()}
      </Routes>
    </Router>
  );
}

export default App;
