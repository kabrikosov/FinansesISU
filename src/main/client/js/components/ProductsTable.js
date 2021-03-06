import React, {useRef, useState, useEffect} from "react";
import {getToken} from "./helpers";

let config = {headers: {'X-CSRF-TOKEN': getToken()}};

export function ProductsTable(props) {
    const data = useRef([]);
    const [status, setStatus] = useState(false);

    useEffect(() => {
        initialize();
    }, [status]);

    function initialize() {
        const req = window.location.origin + "/api/products/all";
        fetch(req, config)
            .then(response => response.json())
            .then(response => {
                data.current = response;
                if (!status)
                    setStatus(!status);
            });
    }

    return (
        <div className="productsTable">
            <table>
                <thead>
                <tr>
                    <td>Name</td>
                    <td>Price</td>
                    <td>Quantity</td>
                    <td>Sum</td>
                    <td>Date</td>
                    <td>Category</td>
                </tr>
                </thead>
                <tbody>
                {data.current.map(el =>
                    <tr>
                        <td><a href={window.location.origin + "/products/" + el?.id}>{el?.name}</a></td>
                        <td>{el?.price}</td>
                        <td>{el?.quantity}</td>
                        <td>{el?.price * el?.quantity}</td>
                        <td>{new Date(el?.date).toLocaleDateString(lang)}</td>
                        <td>{el?.category?.name}</td>
                    </tr>
                )}
                </tbody>
            </table>
        </div>
    );
}