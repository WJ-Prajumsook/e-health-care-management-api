import React, { useEffect } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import { Sidebar } from './components/Sidebar';
import { Navbar } from './components/Navbar';
import { useStateContext } from './contexts/ContextProvider';
import { Appointments } from './pages/Appointments';
import { Patients } from './pages/Patient';
import { Medication } from './pages/Medication';
import { Laboratory } from './pages/Laboratory';
import { TestType } from './pages/TestType';
import { Doctor } from './pages/Doctors';
import { AppointmentsTableView } from './pages/AppointmentsTableview';
import { Specialization } from './pages/Specialization';
import { Footer } from './components/Footer';
import { MedicalHistory } from './pages/MedicalHistory';
import { TooltipComponent } from '@syncfusion/ej2-react-popups';
import { FiSettings } from 'react-icons/fi';
import { Settings } from './components/Settings';

import './App.css';

export const App = () => {
  const { 
    showSidebar, currentColor, setCurrentColor,
    currentMode, setCurrentMode,
    settings, setSettings
  } = useStateContext();

  useEffect(() => {
    const themeColor = localStorage.getItem('themeColor');
    const themeMode = localStorage.getItem('themeMode');
    if(themeColor && themeMode) {
      setCurrentColor(themeColor);
      setCurrentMode(themeMode);
    }
  }, []);

  return(
    <div className={currentMode === 'Dark' ? 'dark' : ''}>
      <BrowserRouter>
        <div className="flex relative dark:bg-main-dark-bg">
          
          {showSidebar ? (
            <div className="w-72 fixed sidebar dark:bg-secondary-dark-bg bg-white">
              <Sidebar />
            </div>
          ) : (
            <div className="w-0 dark:bg-secondary-dark-bg">
              <Sidebar />
            </div>
          )}
          <div className={`dark:bg-main-dark-bg bg-main-bg min-h-screen w-full ${showSidebar ? 'md:ml-72' : 'flex-2'}`}>
            <div className="fixed md:static bg-main-bg dark:bg-main-dark-bg navbar w-full">
              <Navbar />
            </div>
            <div>
              {settings && (<Settings />)}
              <Routes>
                <Route path="/" element={(<Appointments />)} />
                <Route path="/appointment" element={(<Appointments />)} />
                <Route path="/tableview" element={(<AppointmentsTableView />)} />
                <Route path="/patient" element={(<Patients />)} />
                <Route path="/medicalhistory" element={(<MedicalHistory />)} />
                <Route path="/medication" element={(<Medication />)} />
                <Route path="/laboratory" element={(<Laboratory />)} />
                <Route path="/testtype" element={(<TestType />)} />
                <Route path="/doctor" element={(<Doctor />)} />
                <Route path="/specialization" element={(<Specialization />)} />
              </Routes>
            </div>
            <Footer />
          </div>
        </div>
      </BrowserRouter>      
    </div>
  )
};
