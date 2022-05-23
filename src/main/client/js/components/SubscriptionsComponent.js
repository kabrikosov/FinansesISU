import React, {useRef, useState, useEffect} from "react";
import {ProductsTable} from "./ProductsTable";
import {getToken} from "./helpers";

let config = {headers: {'X-CSRF-TOKEN': getToken()}};
const requ = window.location.origin + "/api/statistics"

function representDate(date) {
    date = new Date(date);
    date.setMonth(date.getMonth() + 1);
    return date.toLocaleDateString()
}

export function SubscriptionsComponent() {
    const [summaryData, setSummaryData] = useState(0);
    const [exactData, setExactData] = useState([]);
    const start = useRef(new Date(Date.now()));
    const end = useRef(new Date(Date.now()))
    const [status, setStatus] = useState({loaded: false});

    useEffect(() => {
        start.current = new Date(
            start.current.getFullYear(), start.current.getMonth(), 0
        );
        end.current = new Date(
            end.current.getFullYear(), end.current.getMonth() + 1, 0
        );

        let req1 = requ + "/subscriptionsSum?start=" + start.current.toISOString().substring(0, 10) +
            "&end=" + end.current.toISOString().substring(0, 10);
        let req2 = requ + "/subscriptions?start=" + start.current.toISOString().substring(0, 10) +
            "&end=" + end.current.toISOString().substring(0, 10);
        let req3 = requ + "/subscriptionsGroupping?start=" + start.current.toISOString().substring(0, 10) +
            "&end=" + end.current.toISOString().substring(0, 10);

        const resp1 = fetch(req1, config).then(resp => resp.json());
        const resp2 = fetch(req2, config).then(resp => resp.json());
        const resp3 = fetch(req3, config).then(resp => resp.json());

        Promise.all([resp1, resp2, resp3])
            .then(response => {
                setSummaryData(response[0]);
                response[1].map(el => {
                        el.sum = response[2].find(rel => rel.id === el.id).sum
                    }
                );
                setExactData(response[1]);
            })

    }, [])

    return (
        <div className="statistics">
            <div className="statistics__head">
                <span>Запланировано следующий месяц: </span>
                <span>{summaryData} у.&nbsp;е.</span>
            </div>
            {exactData.map(el =>
                <div className="statistics__item">
                    <span className="item__statistics-name">{el?.name} ({el?.sum} у.&nbsp;е.): </span>
                    <table className="item__product">
                        <tbody>
                        {el?.products.map(prod =>
                            <tr>
                                <td>{prod?.name}</td>
                                <td>{prod?.quantity}x{prod?.price}</td>
                                <td>{prod?.quantity * prod?.price}</td>
                                <td>{
                                    representDate(prod.date)
                                }</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    )
}