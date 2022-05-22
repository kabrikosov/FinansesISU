import React, {useRef, useState, useEffect} from "react";
import {ProductsTable} from "./ProductsTable";

const requ = window.location.origin + "/api/statistics"

export function SubscriptionsComponent() {
    const [summaryData, setSummaryData] = useState(0);
    const [exactData, setExactData] = useState([]);
    const start = useRef(new Date(Date.now()));
    const end = useRef(new Date(Date.now()))
    const [status, setStatus] = useState({summaryData: false, exactData: false});

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

        fetch(req1)
            .then(resp => resp.json())
            .then(resp => {
                setSummaryData(resp);
                console.log(resp);
                if (!status.summaryData) {
                    setStatus({summaryData: true, exactData: status.exactData});
                }
            })
            .catch(console.warn);

        fetch(req2)
            .then(resp => resp.json())
            .then(resp => {
                setExactData(resp);
                console.log(resp);
                if (!status.exactData) {
                    setStatus({summaryData: status.summaryData, exactData: true});
                }
            })
            .catch(console.warn);
    }, [status])

    return (
        <div className="statistics">
            <div className="statistics__head">
                <span>Запланировано следующий месяц: </span>
                <span>{summaryData} у.&nbsp;е.</span>
            </div>
            <div className="statistics_items">
                {exactData.map(el =>
                    <div className="statistics__item">
                        <span className="item__statistics-name">{el.name}: </span>
                        {el.products.map(prod =>
                            <div className="item__product">
                                <span>{prod.name}</span>
                                <span> {prod.quantity}x{prod.price}={prod.quantity*prod.price}</span>
                            </div>
                        )}
                    </div>
                )}
            </div>
        </div>
    )
}