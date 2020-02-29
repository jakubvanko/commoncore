import React, {Suspense} from "react";
const CommonCore = React.lazy(() => import("../scenes/CommonCore/CommonCore"));
import Loader from "../components/Loader/Loader";

const Home = () => (
    <Suspense fallback={<Loader/>}>
        <CommonCore/>
    </Suspense>
);

export default Home;
