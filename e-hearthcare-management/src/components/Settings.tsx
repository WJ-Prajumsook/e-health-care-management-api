import React from 'react';
import { MdOutlineCancel } from 'react-icons/md';
import { BsCheck } from 'react-icons/bs';
import { TooltipComponent } from '@syncfusion/ej2-react-popups';
import { useStateContext } from '../contexts/ContextProvider';

export const Settings = () => {
  const {
    setColor, setMode,
    currentColor, currentMode,
    setSettings
  } = useStateContext();

  const inputRadio = (id: string, value: string, checked: boolean) => {
    return (
      <>
        <input type="radio"
              id={id}
              name="theme"
              value={value}
              className="cursor-pointer"
              onChange={setMode}
              checked={checked} />
        <label htmlFor='light' className="ml-2 text-md cursor-pointer">
          {value}
        </label>
      </>
    );
  };

  return (
    <div className="bg-half-transparent w-screen fixed nav-item top-0 right-0">
      <div className="float-right h-screen dark:text-gray-200 bg-white dark:bg-[#484B52] w-400">
        <div className="flex justify-between items-center p-4 ml-4">
          <p className="font-semibold text-lg">Settings</p>
          <button type="button"
            onClick={() => setSettings(false)}
            style={{ color: 'rgb(153,171,180)', borderRadius: '50%' }}
            className="text-2xl p-3 hover:drop-shadow-xl hover:bg-light-gray">
            <MdOutlineCancel />
          </button>
        </div>
        <div className="flex-col border-t-1 border-color p-4 ml-4">
          <p className="font-semibold text-xl">Modes</p>
          <div className="mt-4">
            {inputRadio('light', 'Light', (currentMode === 'Light'))}  
          </div>
          <div className="mt-2">
            {inputRadio('dark', 'Dark', (currentMode === 'Dark'))}
          </div>  
        </div>
        <div className="p-4 border-t-1 border-color ml-4">
          <p className="font-semibold text-xl">Colors</p>
          <div className="flex gap-3">
            {colors.map((item, index) => (
              <TooltipComponent key={index} content={item.name} position="TopCenter">
                <div className="relative mt-2 cursor-pointer flex gap-5 items-left" key={item.name}>
                  <button type="button"
                    className="h-10 w-10 rounded-full cursor-pointer"
                    style={{ backgroundColor: item.color }}
                    onClick={() => setColor(item.color )}>
                    <BsCheck className={`ml-2 text-2xl text-white ${item.color === currentColor ? 'block' : 'hidden'}`} />
                  </button>
                </div>
              </TooltipComponent>
            ))}
          </div>
        </div>  
      </div>
    </div>
  );
};

const colors = [
  {
    name: 'blue-theme',
    color: '#1A97F5',
  },
  {
    name: 'green-theme',
    color: '#03C9D7',
  },
  {
    name: 'purple-theme',
    color: '#7352FF',
  },
  {
    name: 'red-theme',
    color: '#FF5C8E',
  },
  {
    name: 'indigo-theme',
    color: '#1E4DB7',
  },
  {
    color: '#FB9678',
    name: 'orange-theme',
  }
];
