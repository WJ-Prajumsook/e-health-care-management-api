import React, { useState, useEffect } from 'react';
import { Header } from '../components/Header';
import { 
  ScheduleComponent,
  ViewsDirective,
  ViewDirective,
  Day, Week, WorkWeek, Month,
  Agenda, Inject, Resize, DragAndDrop
} from '@syncfusion/ej2-react-schedule';
import { DatePickerComponent } from '@syncfusion/ej2-react-calendars';
import { DataManager, WebApiAdaptor, Query } from '@syncfusion/ej2-data';
import { ButtonComponent } from '@syncfusion/ej2-react-buttons';
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns';

const PropertyPane = (props: any) => <div className="mt-5">{props.children}</div>

const EditorTemplate = (props: any) => {
  return(
    <table className='customer-event-editor' style={{width: '100%'}}>
    <tbody>
      <tr>
        <td className='e-textlabel'>Patient ID</td>
        <td colSpan={4}>
        <input id='patientId' className='e-input' type='text' name='patientId' style={{width: '100%'}} />
        </td>
      </tr>
      <tr>
        <td className='e-textlabel'>Doctor ID</td>
        <td colSpan={4}>
        <input id='doctorId' className='e-input' type='text' name='doctorId' style={{width: '100%'}} />
        </td>
      </tr>
      <tr>
        <td className='e-textlabel'>Appointment type</td>
        <td colSpan={4}>
          <DropDownListComponent id='appointmentType' placeholder='Choose appointment type' data-name='appointmentType' style={{width: '100%'}}
              dataSource={['Checkup', 'Emergency', 'Follow up', 'Routine', 'Walk in']} value={props.appointmentType || null} />
        </td>
      </tr>
      <tr>
        <td className='e-textlabel'>Start date</td>
        <td colSpan={4}>
          <DatePickerComponent format='dd/MM/yy hh:mm a' id='startTime' data-name='startTime' value={new Date(props.startTime || props.StartTime)} />
        </td>
      </tr>
      <tr>
        <td className='e-textlabel'>End date</td>
        <td colSpan={4}>
          <DatePickerComponent format='dd/MM/yy hh:mm a' id='endTime' data-name='endTime' value={new Date(props.endTime || props.endTime)} />    
        </td>
      </tr>    
    </tbody>
  </table>);
}

export const Appointments = () => {
  const [scheduleObj, setScheduleObj] = useState<any>();
  const [btnObj, setBtnObj] = useState<any>();
  const [dataSource, setDataSource] = useState<any>();

  useEffect(() => {
    const fetchAppointments = async () => {
      const response = await fetch('http://192.168.64.2:31747/ehealthcare/v1/appointments');
      setDataSource(response);
    };

    fetchAppointments();
  }, []);

  const onDragStart = (arg: any) => {
    arg.navigation.enable = true;
  }

  const change = (args: any) => {
    scheduleObj.selectedDate = args.value;
    scheduleObj.dataBind();
  }

  /*const dataManager: DataManager = new DataManager({
    adaptor: new WebApiAdaptor(),
    //url: "http://192.168.64.13:31799/api/v1/appointments",
    url: "http://localhost:8080/api/v1/appointments",
});*/

  const onPopupClose = (args: any) => {
    /*if(args.type === 'Editor' && args.data !== 'undefined') {
      let patientId;
      let doctorId;
      let appointmentType;
      let startDate;
      let endDate;

      let el = args.element.querySelector('#patientId');
      if(el) {
        patientId = el.value;
      }
      el = args.element.querySelector('#doctorId');
      if(el) {
        doctorId = el.value;
      }

      console.log(patientId + " " + doctorId);
}*/
    if(args.type === 'Editor' && args.data !== 'undefined') {
      let el = args.element.querySelector('#patientId');
      console.log(el.value);
    } 
  }

  return(
    <div className="m-2 md:m-10 mt-24 p-2 md:p-10 bg-white rounded-3xl">
      <Header category="Dashboard" title="Appointments" /> 
      <ScheduleComponent
        height="650px"
        ref={(schedule) => setScheduleObj(schedule)}
        selectedDate={new Date()}
        eventSettings={{ dataSource: dataSource }}
        dragStart={onDragStart}
        //timezone="Europe/Stockholm"
        editorTemplate={EditorTemplate.bind(this)}
        showQuickInfo={false}
        popupClose={onPopupClose.bind(this)}
      >
        <ViewsDirective>
          { ['Day', 'Week', 'WorkWeek', 'Month', 'Agenda'].map((item: any) => <ViewDirective key={item} option={item} />)}
        </ViewsDirective>
        <Inject services={[Day, Week, WorkWeek, Month, Agenda, Resize, DragAndDrop]} />  
      </ScheduleComponent>
      <PropertyPane>
        <table style={{ width: '100%', background: 'white' }}>
          <tbody>
            <tr style={{ height: '50px' }}>
              <td style={{ width: '100%' }}>
                <DatePickerComponent
                  value={new Date()}
                  showClearButton={false}
                  placeholder="Current Date"
                  floatLabelType="Always"
                  change={change}
                />
              </td>
            </tr>
          </tbody>
        </table>    
      </PropertyPane>
    </div>
  );
};

const schedules = [
  {
    id: 1,
    Subject: 'Explosion of Betelgeuse Star',
    StartTime: '2022-08-18T04:00:00.000Z',
    EndTime: '2022-08-18T05:30:00.000Z',
  }
]

const scheduleData = [
  {
    Id: 1,
    Subject: 'Explosion of Betelgeuse Star',
    Location: 'Space Center USA',
    StartTime: '2022-08-10T04:00:00.000Z',
    EndTime: '2022-08-10T05:30:00.000Z',
    CategoryColor: '#1aaa55',
  },
  {
    Id: 2,
    Subject: 'Thule Air Crash Report',
    Location: 'Newyork City',
    StartTime: '2022-08-11T06:30:00.000Z',
    EndTime: '2022-08-11T08:30:00.000Z',
    CategoryColor: '#357cd2',
  },
  {
    Id: 3,
    Subject: 'Blue Moon Eclipse',
    Location: 'Space Center USA',
    StartTime: '2022-08-12T04:00:00.000Z',
    EndTime: '2022-08-12T05:30:00.000Z',
    CategoryColor: '#7fa900',
  },
  {
    Id: 4,
    Subject: 'Meteor Showers in 2021',
    Location: 'Space Center USA',
    StartTime: '2022-08-13T07:30:00.000Z',
    EndTime: '2022-08-13T09:00:00.000Z',
    CategoryColor: '#ea7a57',
  },
  {
    Id: 5,
    Subject: 'Milky Way as Melting pot',
    Location: 'Space Center USA',
    StartTime: '2022-08-14T06:30:00.000Z',
    EndTime: '2022-08-14T08:30:00.000Z',
    CategoryColor: '#00bdae',
  },
  {
    Id: 6,
    Subject: 'Mysteries of Bermuda Triangle',
    Location: 'Bermuda',
    StartTime: '2022-08-14T04:00:00.000Z',
    EndTime: '2022-08-14T05:30:00.000Z',
    CategoryColor: '#f57f17',
  },
  {
    Id: 7,
    Subject: 'Glaciers and Snowflakes',
    Location: 'Himalayas',
    StartTime: '2022-08-15T05:30:00.000Z',
    EndTime: '2022-08-15T07:00:00.000Z',
    CategoryColor: '#1aaa55',
  },
  {
    Id: 8,
    Subject: 'Life on Mars',
    Location: 'Space Center USA',
    StartTime: '2022-08-16T03:30:00.000Z',
    EndTime: '2022-08-16T04:30:00.000Z',
    CategoryColor: '#357cd2',
  },
  {
    Id: 9,
    Subject: 'Alien Civilization',
    Location: 'Space Center USA',
    StartTime: '2022-08-18T05:30:00.000Z',
    EndTime: '2022-08-18T07:30:00.000Z',
    CategoryColor: '#7fa900',
  },
  {
    Id: 10,
    Subject: 'Wildlife Galleries',
    Location: 'Africa',
    StartTime: '2022-08-20T05:30:00.000Z',
    EndTime: '2022-08-20T07:30:00.000Z',
    CategoryColor: '#ea7a57',
  }
];
