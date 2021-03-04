import React, { useState } from 'react';
import './AdminNavbar.css';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import { Link, useHistory } from 'react-router-dom';
import { IconContext } from 'react-icons';
import { AdminSidebarData } from './AdminSidebarData';
import axios from 'axios';
import { Nav, NavDropdown } from 'react-bootstrap';

function capitalizeFirstLetter(word) {
    return word.charAt(0).toUpperCase() + word.slice(1);
}

function AdminNavbar() {
    const [sidebar, setSidebar] = useState(false)
    const showSidebar = () => setSidebar(!sidebar)
    const adminData = JSON.parse(localStorage.getItem('info'))

    const [adminName, setAdminName] = useState();

    axios.get('http://localhost:8080/auth/adminvalidate', {
        headers: {
            'Authorization': `Bearer ${adminData.token}`
        }
    })
        .then(res => {
            setAdminName(capitalizeFirstLetter(res.data.name))
        })
        .catch(err => {
            console.log(err)
        })

    const history = useHistory();

    const handleLogout = () => {
        localStorage.clear();
        history.push('/')
    }


    return (
        <>
            <IconContext.Provider value={{ color: '#fff' }}>
                <div className="navbar">
                    <Link to="#" className='menu-bars'>
                        <FaIcons.FaBars onClick={showSidebar} />
                    </Link>
                    <h1 className="navbar-logo">Contract Management<i className="fab fa-react"></i></h1>
                    {localStorage ? (
                        <Nav>
                            <NavDropdown
                                title={adminName}
                                style={{
                                    "backgroundColor": "black",
                                    "marginRight": "12px",
                                    "fontWeight": "bold",
                                    "borderRadius": "10%"
                                }}>
                                <NavDropdown title="Generate Reports">
                                    <NavDropdown.Item href="/listactivecontracts">Contracts</NavDropdown.Item>
                                    <NavDropdown.Item href="/listsuppliers">Suppliers</NavDropdown.Item>
                                </NavDropdown>
                                <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    ) : null}
                </div>
                <nav className={sidebar ? 'nav-menu active' : 'nav-menu'}>
                    <ul className='nav menu-items' onClick={showSidebar}>
                        <li className='navbar-toggle'>
                            <Link to="#" className='menu-bars'>
                                <AiIcons.AiOutlineClose />
                            </Link>
                        </li>
                        {AdminSidebarData.map((item, index) => {
                            return (
                                <li key={index} className={item.className}>
                                    <Link to={item.path}>
                                        {item.icon}
                                        <span>{item.title}</span>
                                    </Link>
                                </li>
                            )
                        })}
                    </ul>
                </nav>
            </IconContext.Provider>
        </>

    )
}

export default AdminNavbar