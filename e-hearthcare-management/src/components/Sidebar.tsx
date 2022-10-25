import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import { SiWorldhealthorganization } from 'react-icons/si';
import { MdOutlineCancel, MdOutlineMedicalServices } from 'react-icons/md';
import { TooltipComponent } from '@syncfusion/ej2-react-popups';
import { 
  FcCalendar, 
  FcPortraitMode, 
  FcDataSheet, 
  FcLike, FcDebt, 
  FcBiotech, 
  FcElectricalSensor, 
  FcBusinessman 
} from 'react-icons/fc';
import { useStateContext } from '../contexts/ContextProvider';

export const Sidebar = () => {
  const { showSidebar, setShowSidebar, currentColor, screenSize } = useStateContext();

  const handleHideSidebar = () => {
    if(showSidebar && screenSize <= 900) {
      setShowSidebar(false);
    }
  };

  const activeLink = 'flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-white text-md m-2';
  const normalLink = 'flex items-center gap-5 pl-4 pt-3 pb-2.5 rounded-lg text-md text-gray-700 dark:text-gray-200 dark:hover:text-black hover:bg-light-gray m-2';

  return(
    <div className="ml-3 h-screen md:overflow-hidden overflow-auto md:hover:overflow-auto pb-10">
      {showSidebar && (
        <>
          <div className="flex justify-between items-center">
            <Link to="/" onClick={() => {}}
              className="items-center gap-3 ml-3 mt-4 flex text-xl font-extrabold tracking-tight dark:text-white text-slate-900">
              <SiWorldhealthorganization /> <span>e-Healthcare</span>
            </Link>
            <TooltipComponent content="Menu" position="BottomCenter">
              <button type="button"
                onClick={() => setShowSidebar(false)}
                style={{ color: currentColor }}
                className="text-xl rounded-full p-3 hover:bg-light-gray mt-4 block md:hidden">
                <MdOutlineCancel />
              </button>
            </TooltipComponent>  
          </div>
          <div className="mt-10">
            {links.map((item, index) => (
              <div key={index}>
                <p className="text-gray-400 dark:text-gray-400 m-3 mt-4 uppercase">
                  {item.title}
                </p>
                {item.links.map((link) => (
                  <NavLink to={`/${link.name}`}
                    key={link.name}
                    onClick={() => handleHideSidebar}
                    style={({ isActive }) => ({
                      backgroundColor: isActive ? currentColor : '',
                    })}
                    className={({ isActive }) => (isActive ? activeLink : normalLink)}>
                    {link.icon}
                    <span className="capitalize">{link.name}</span>
                  </NavLink>
                ))}  
              </div>
            ))}
          </div>  
        </>
      )}
    </div>
  )
};

const links = [
    {
    title: 'Dashboard',
    links: [
      {
        name: 'appointment',
        icon: <FcCalendar />,
      },
      {
        name: 'tableview',
        icon: <FcCalendar />,
      },
    ],
  },
  {
    title: 'Patients',
    links: [
      {
        name: 'patient',
        icon: <FcPortraitMode />,
      },
      {
        name: 'medicalhistory',
        icon: <FcDataSheet />,
      }
    ]
  },
  {
    title: 'Medication',
    links: [
      {
        name: 'medication',
        icon: <FcLike />,
      }
    ]
  },
  {
    title: 'Laboratory',
    links: [
      {
        name: 'laboratory',
        icon: <FcBiotech />,
      },
      {
        name: 'testtype',
        icon: <FcElectricalSensor />,
      }
    ]
  },
  {
    title: 'Doctors',
    links: [
      {
        name: 'doctor',
        icon: <FcBusinessman />,
      },
      {
        name: 'specialization',
        icon: <FcDebt />,
      }
    ],
  }
]
