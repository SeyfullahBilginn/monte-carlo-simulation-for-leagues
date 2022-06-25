import Home from './Pages/Home';
import Landing from './Pages/Landing';

const routes = [
    {
        name: "Home",
        key: "home",
        route: "/home",
        component: <Home />,
    },
    {
        name: "landing",
        key: "landing",
        route: "/",
        component: <Landing />,
    },
]

export default routes;