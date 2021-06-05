import React, { useState } from 'react';
import axios from 'axios';
import './SearchResults.css';

function SearchResults(props) {
    const [queryResults, setQueryResults] = useState(null);
    const [queryText] = useState(props.text);

    // axios.defaults.baseURL = "localhost:9200";
    // axios.get("/search/" + queryText).then(response => {
    //     debugger;
    // }).catch(error => {
    //     debugger;
    // });

    const searchText = (e) => {
        let text = document.getElementById("search-bar").value;
        console.log(encodeURIComponent(text));
        window.location.href = "/" + encodeURIComponent(text);
    }

    const redirectToPage = (e) => {
        window.location.href = e.currentTarget.parentElement.getElementsByTagName("a")[0].href;
    }

    return (
        <div id="search-results">
            <div className="container-fluid">
                <div className="row d-flex">
                    <div className="col-3 d-flex align-items-center">
                        <h1 className="m-0">Google It</h1>
                    </div>
                    <div className="col-9 d-flex align-items-center">
                        <input type="text" className="form-control w-50 " defaultValue={queryText} />
                        <button className="ms-3 btn btn-primary" onClick={searchText}>Search</button>
                    </div>
                </div>
                <hr />
                <div className="row mx-3">
                    <div className="col-8 my-2">
                        {/* <small className="cursor-pointer" onClick={redirectToPage}>https://stackoverflow.com/questions/1714786/query-string-encoding-of-a-javascript-object</small> */}
                        <br />
                        <a href="https://stackoverflow.com/questions/1714786/query-string-encoding-of-a-javascript-object" className="fs-4 cursor-pointer">Query-string encoding of a Javascript Object - Stack Overflow</a>
                        <p className="m-0">Mar 18, 2021 — Parse and stringify URL query strings. ... query-string. TypeScript icon, indicating that this package has built-in type declarations...</p>
                    </div>
                    <div className="col-8 my-2">
                        {/* <small className="cursor-pointer" onClick={redirectToPage}>https://stackoverflow.com/questions/1714786/query-string-encoding-of-a-javascript-object</small> */}
                        <br />
                        <a href="https://stackoverflow.com/questions/1714786/query-string-encoding-of-a-javascript-object" className="fs-4 cursor-pointer">Query-string encoding of a Javascript Object - Stack Overflow</a>
                        <p className="m-0">Mar 18, 2021 — Parse and stringify URL query strings. ... query-string. TypeScript icon, indicating that this package has built-in type declarations...</p>
                    </div>
                    <div className="col-8 my-2">
                        {/* <small className="cursor-pointer" onClick={redirectToPage}>https://stackoverflow.com/questions/1714786/query-string-encoding-of-a-javascript-object</small> */}
                        <br />
                        <a href="https://stackoverflow.com/questions/1714786/query-string-encoding-of-a-javascript-object" className="fs-4 cursor-pointer">Query-string encoding of a Javascript Object - Stack Overflow</a>
                        <p className="m-0">Mar 18, 2021 — Parse and stringify URL query strings. ... query-string. TypeScript icon, indicating that this package has built-in type declarations...</p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SearchResults;