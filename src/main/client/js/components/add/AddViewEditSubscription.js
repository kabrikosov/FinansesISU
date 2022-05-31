import React, {useState, useEffect} from "react";
import {strings} from "../Strings";
import {getToken, representDate} from "../helpers";
import {useParams} from "react-router-dom";
import {TableRow} from "../TableRow";

const requ = window.location.origin + '/api/subscriptions';

let postHeaders = {'X-CSRF-TOKEN': getToken(), 'Content-Type': 'application/json; charset=UTF-8'};
const getConfig = {headers: {'X-CSRF-TOKEN': getToken()}};

export function AddViewEditSubscription() {
    const [state, setState] = useState({
        name: '',
        startDateTime: new Date().toISOString().substr(0, 16),
        expirationDateTime: new Date().toISOString().substr(0, 16)
    });
    const [data, setData] = useState({products: []});
    const [status, setStatus] = useState({loaded: false});
    let {id} = useParams();

    function deleteSubscription(e) {
        e.preventDefault();
        let req = requ + `/${id}/delete`;
        fetch(req, {
            method: 'Delete',
            headers: {'X-CSRF-TOKEN': getToken()}
        }).then(resp => {
            if (resp.status === 200)
                window.location.replace(window.location.origin + '/subscriptions');
        }).catch(console.warn);
    }

    function saveSubscription(e) {
        e.preventDefault();
        const subscription = {
            name: state.name,
            startDateTime: state.startDateTime,
            expirationDateTime: state.expirationDateTime
        };

        let method = (id == null) ? 'POST' : 'PUT';
        let req = requ + ((id == null) ? "/add" : `/${id}/put`)

        fetch(req, {
            body: JSON.stringify(subscription),
            headers: postHeaders,
            method: method
        })
            .then(resp => {
                console.log(resp)
            })
            .catch(err => {
                console.log(err)
            });
    }

    function paramsAreSpecified() {
        let req1 = window.location.origin + "/api/subscriptions/" + id;

        fetch(req1, getConfig).then(resp => resp.json())
            .then(resp => {
                setState({
                    name: resp.name,
                    startDateTime: resp.startDateTime?.toISOString?.().substr?.(0, 16),
                    expirationDateTime: resp.expirationDateTime?.toISOString?.().substr?.(0, 16)
                });
                setData({
                    products: resp.products
                });
                if (!status)
                    setStatus(!status);
            })
            .catch(console.warn);
    }

    function update(){
        setStatus(!status);
    }


    useEffect(() => {
        if (id != null)
            paramsAreSpecified();
    }, [status])

    return (
        <div className="content__add-form-wrapper">
            <div className="content__add-form">
                <form>
                    <div className="form__field">
                        <label htmlFor="name">{strings[lang].model.name}</label>
                        <input
                            id="name"
                            type="text"
                            name="name"
                            value={state.name}
                            onChange={e => setState({
                                name: e.target.value,
                                startDateTime: state.startDateTime,
                                expirationDateTime: state.expirationDateTime
                            })}
                        />
                    </div>
                    <div className="form__field">
                        <label htmlFor="quantity">{strings[lang].model.startDate}</label>
                        <input id="startDateTime" type="datetime-local" name="startDateTime"
                               value={state.startDateTime}
                               onChange={e => setState({
                                   name: state.name,
                                   startDateTime: e.target.value,
                                   expirationDateTime: state.expirationDateTime
                               })}
                        />
                    </div>
                    <div className="form__field">
                        <label htmlFor="price">{strings[lang].model.expirationDate}</label>
                        <input id="expirationDateTime" type="datetime-local" name="expirationDateTime"
                               value={state.expirationDateTime}
                               onChange={e => setState({
                                   name: state.name,
                                   startDateTime: state.startDateTime,
                                   expirationDateTime: e.target.value
                               })}
                        />
                    </div>
                    <button onClick={saveSubscription} className="form__submit-button"
                            type="submit">{strings[lang].forms.submit}</button>
                    {(id != null) &&
                    <button onClick={deleteSubscription} className="form__submit-button form__submit-button--delete"
                            type="submit">{strings[lang].forms.delete}</button>}
                </form>
            </div>
            <div className="subscription__products-list">
                <table className="item__product">
                    {data.products.map(prod =>
                        <TableRow data={prod} dataType="products" onUpdate={update}/>
                        // <tr>
                        //     <td><a href={window.location.origin + `/products/${prod.id}`}>{prod?.name}</a></td>
                        //     <td>{prod?.quantity}x{prod?.price}</td>
                        //     <td>{prod?.quantity * prod?.price}</td>
                        //     <td>{representDate(prod.date)}</td>
                        // </tr>
                    )}
                </table>
            </div>
        </div>
    )
}