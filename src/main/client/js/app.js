import React from 'react'
import {createRoot} from 'react-dom/client'
import {FinanceCircle} from "./components/FinanceCircle";
import {ProductsTable} from "./components/ProductsTable";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {AddProduct} from "./components/add/AddProduct";
import {StatisticsComponent} from "./components/StatisticsComponent";

const root = createRoot(document.getElementById("app"));
root.render(
    <Router>
        <Routes>
            <Route exact path="/" element={<StatisticsComponent />}/>
            <Route path="home" element={<StatisticsComponent />}/>
            <Route path="products">
                <Route exact path="" element={<ProductsTable />} />
                <Route path="add" element={<AddProduct />}/>
            </Route>
        </Routes>
    </Router>
)