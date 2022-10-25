import React, { createContext, useContext, useState } from 'react';

const StateContext = createContext<any>({});

export const ContextProvider = ({ children }: any) => {
  const [showSidebar, setShowSidebar] = useState(true);
  const [screenSize, setScreenSize] = useState(undefined);
  const [currentColor, setCurrentColor] = useState("#03C9D7");
  const [currentMode, setCurrentMode] = useState('Light');
  const [settings, setSettings] = useState(false);

  const setMode = (mode: any) => {
    setCurrentMode(mode.target.value);
    localStorage.setItem('themeMode', mode.target.value);
  };
  const setColor = (color: any) => {
    setCurrentColor(color);
    localStorage.setItem('themeColor', color);
  };

  return (
    <StateContext.Provider value={{ 
      showSidebar, setShowSidebar,
      screenSize, setScreenSize,  
      currentColor, setCurrentColor,
      currentMode, setCurrentMode,
      settings, setSettings,
      setColor, setMode
    }}>
      {children}
    </StateContext.Provider>
  )
};

export const useStateContext = () => useContext(StateContext);
