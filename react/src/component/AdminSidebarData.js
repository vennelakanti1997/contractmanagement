import React from 'react'
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';

export const AdminSidebarData = [
    {
        title: 'Home',
        path: '/admindetails',
        icon: <AiIcons.AiFillHome />,
        className: 'nav-text'
    },
    {
        title: 'Create Requirement',
        path: '/createrequirement',
        icon: <FaIcons.FaCartPlus />,
        className: 'nav-text'
    },
    {
        title: 'View Requirement',
        path: '/viewrequirements',
        icon: <FaIcons.FaCartPlus />,
        className: 'nav-text'
    },
    {
        title: 'View Proposals',
        path: '/viewproposaladmin',
        icon: <IoIcons.IoMdPeople />,
        className: 'nav-text'
    },
    {
        title: 'View Contracts',
        path: '/viewcontracts',
        icon: <FaIcons.FaCartPlus />,
        className: 'nav-text'
    },
    {
        title: 'View Delivery',
        path: '/managedelivery',
        icon: <IoIcons.IoMdPeople />,
        className: 'nav-text'
    }
]


