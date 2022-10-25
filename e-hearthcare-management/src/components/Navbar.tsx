import React, { useEffect } from 'react';
import { TooltipComponent } from '@syncfusion/ej2-react-popups';
import { AiOutlineMenu } from 'react-icons/ai';
import { FiSettings } from 'react-icons/fi';

import { useStateContext } from '../contexts/ContextProvider';

export const Navbar = () => {
  const { showSidebar, setShowSidebar, currentColor, setSettings, setScreenSize, screenSize } = useStateContext();

  useEffect(() => {
    const handleResize = () => setScreenSize(window.innerWidth);
    window.addEventListener('resize', handleResize);

    handleResize();

    return () => window.removeEventListener('resize', handleResize);
  }, []);

  useEffect(() => {
    if(screenSize <= 900) {
      setShowSidebar(false);
    } else {
      setShowSidebar(true);
    }
  }, [screenSize]);

  return(
    <div className="flex justify-between p-2 md:ml-6 relative">
      <TooltipComponent content="Menu" position="BottomCenter">
        <button type="button"
          onClick={() => setShowSidebar(!showSidebar)}
          style={{color: currentColor}}
          className="relative text-xl rounded-full p-3 hover:bg-light-gray">
          <AiOutlineMenu />
        </button>
      </TooltipComponent>
      <TooltipComponent content="Settings" position="TopRight">
        <button type="button"
          onClick={() => setSettings(true)}
          className="text-xl rounded-full p-3 hover:bg-light-gray"
          style={{ color: currentColor }}>
          <FiSettings />
        </button>
      </TooltipComponent>
    </div>
  )
};
