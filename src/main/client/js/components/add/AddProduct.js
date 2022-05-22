import React, {useRef, useState, useEffect} from "react";

const requ = window.location.origin + '/api/products/add';
// const init = {method: 'POST', headers: {'X-CSRF-TOKEN': token}};

const strings = {
    "en": {
        name: 'Name',
        price: 'Price',
        quantity: 'Quantity',
        date: 'Date',
        category: 'Category',
        subscription: 'Subscription',
        selectOption: "Select one option from list",
        submit: "Submit"
    }, "ru": {
        name: 'Название',
        price: 'Цена',
        quantity: 'Количество',
        date: 'Дата',
        category: 'Категория',
        subscription: 'Подписка',
        selectOption: "Выберите один элемент из списка",
        submit: "Отправить"
    }
}

export function AddProduct() {
    const [name, setName] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [price, setPrice] = useState(1);
    const [date, setDate] = useState(new Date());
    const [category, setCategory] = useState(null);
    const [subscription, setSubscription] = useState(null);
    const [data, setData] = useState({categories: [], subscriptions: []});
    const [status, setStatus] = useState({categories: false, subscriptions: false});


    function saveProduct(e) {
        e.preventDefault();
        const product = {
            name: name,
            quantity: quantity,
            price: price,
            date: date.toISOString().substr(0, 16),
            category: category,
            subscription: subscription
        };
        fetch(requ, {
            body: JSON.stringify(product),
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST'
        })
            .then(resp => {
                console.log(resp)
            })
            .catch(err => {
                console.log(err)
            });
    }

    useEffect(() => {
        let req1 = window.location.origin + "/api/categories/all"
        let req2 = window.location.origin + "/api/subscriptions/all"

        fetch(req1)
            .then(resp => resp.json())
            .then(resp => {
                setData({categories: resp, subscriptions: data.subscriptions});
                if (!status.categories){
                    setStatus({categories: true, subscriptions: status.subscriptions});
                }
            })
            .catch(console.log)

        fetch(req2)
            .then(resp => resp.json())
            .then(resp => {
                setData({subscriptions: resp, categories: data.categories});
                if (!status.subscriptions){
                    setStatus({subscriptions: true, categories: status.categories});
                }
            });
    }, [status])

    return (
        <div className="content__add-form">
            <form>
                <div className="form__field">
                    <label htmlFor="name">{strings[lang].name}</label>
                    <input
                        id="name"
                        type="text"
                        name="name"
                        value={name}
                        onChange={e => setName(e.target.value)}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="quantity">{strings[lang].quantity}</label>
                    <input id="quantity" type="number" name="quantity"
                           value={quantity}
                           onChange={e => setQuantity(e.target.value)}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="price">{strings[lang].price}</label>
                    <input id="price" type="number" name="price"
                           value={price}
                           onChange={e => setPrice(e.target.value)}
                    />
                </div>
                <div className="form__field">
                    <label htmlFor="date">{strings[lang].date}</label>
                    <input id="date" type="datetime-local" name="date"
                           value={date.toISOString().substr(0, 16)}
                           onChange={e => setDate(e.target.value)}/>
                </div>
                <div className="form__field">
                    <label htmlFor="category">{strings[lang].category}</label>
                    <select id="category" name="category"
                            value={category}
                            onChange={e => setCategory(e.target.value)}>
                        <option selected value={null}>{strings[lang].selectOption}</option>
                        {data.categories.map(el =>
                            <option value={el.id}>{el.name}</option>
                        )}
                    </select>
                </div>
                <div className="form__field">
                    <label htmlFor="subscription">{strings[lang].subscription}</label>
                    <select id="subscription" name="subscription"
                            value={subscription}
                            onChange={e => setSubscription(e.target.value)}>
                        <option selected value={null}>{strings[lang].selectOption}</option>
                        {data.subscriptions.map(el =>
                            <option value={el.id}>{el.name}</option>
                        )}
                    </select>
                </div>
                <button onClick={saveProduct} className="form__submit-button"
                        type="submit">{strings[lang].submit}</button>
            </form>
        </div>)
}