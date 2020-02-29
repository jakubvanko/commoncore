import React from "react";
import CommonCore from "../scenes/CommonCore/CommonCore";
// React Suspense is not yet supported for server-side React
// and therefore Docusaurus cannot build this if we use it.
// Once it is supported, use the Loader component for react suspense.

const Home = () => (
    <CommonCore/>
);

export default Home;
