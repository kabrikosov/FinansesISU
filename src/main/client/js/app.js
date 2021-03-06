import React from 'react'
import {createRoot} from 'react-dom/client'
import {ProductsTable} from "./components/ProductsTable";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {AddViewEditProduct} from "./components/add/AddViewEditProduct";
import {StatisticsComponent} from "./components/StatisticsComponent";
import {LogInComponent} from "./components/auth/LogInComponent";
import {RegisterComponent} from "./components/auth/Registration";
import {AddViewEditSubscription} from "./components/add/AddViewEditSubscription"

const root = createRoot(document.getElementById("app"));
root.render(
    <Router>
        <Routes>
            <Route exact path="/" element={<StatisticsComponent />}/>
            <Route path="home" element={<StatisticsComponent />}/>
            <Route path="/login" element={<LogInComponent />} />
            <Route path="/register" element={<RegisterComponent />} />
            <Route path="/products">
                <Route path=":id" element={<AddViewEditProduct />} />
                <Route path="add" element={<AddViewEditProduct />}/>
                <Route exact path="" element={<ProductsTable />} />
            </Route>
            <Route path="/subscriptions">
                <Route path=":id" element={<AddViewEditSubscription />} />
                <Route path="add" element={<AddViewEditSubscription />} />
            </Route>
        </Routes>
    </Router>
)