import React from 'react'
import { Link } from 'react-router-dom';
import HomeNavbar from '../component/HomeNavbar';
import './Home.css'

// export let Role = "";

function Home() {

    const clearLocalStorage = () => {
        localStorage.removeItem('info')
    }


    return (
        <div>
            <HomeNavbar />
            <div className="hero">
                <h1>What is your role??</h1>
                <div className="links">
                    <Link to={"/login/supplier"}
                        onClick={clearLocalStorage}
                        type="button"
                        class="btn btn-outline-success"
                    >Supplier</Link>

                    <Link to={"/login/admin"}
                        onClick={clearLocalStorage}
                        type="button"
                        className=" btn btn-outline-danger"
                    >Contract Admin</Link>
                </div>

            </div>


        </div >
    )
}

export default Home
