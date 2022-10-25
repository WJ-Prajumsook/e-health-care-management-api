import React from 'react';
import { Header } from '../components/Header';
import { GridComponent, ColumnsDirective, ColumnDirective, Page, Selection,
  Inject, Edit, Toolbar, Sort, Filter, CommandColumn
} from '@syncfusion/ej2-react-grids';
import { DataManager, WebApiAdaptor } from '@syncfusion/ej2-data';
import { selectionsettings, toolbarOptions, editing, commands } from '../utils/DataBindingUtil';

export const AppointmentsTableView = () => {
  const dataManager: DataManager = new DataManager({
    adaptor: new WebApiAdaptor(),
    url: "http://192.168.64.2:31747/ehealthcare/v1/appointments",
    //url: "http://localhost:8080/ehealthcare/v1/appointments",
  });

  return(
    <div className="m-2 md:m-10 mt-24 p-2 md:p-10 bg-white rounded-3xl">
      <Header category="Appointments" title="Table View" />
      <GridComponent
        dataSource={dataManager}
        enableHover={false}
        allowPaging
        pageSettings={{ pageCount: 5 }}
        selectionSettings={selectionsettings}
        toolbar={toolbarOptions}
        editSettings={editing}
        allowSorting
      >
        <ColumnsDirective>
          <ColumnDirective field='id' headerText='ID' width='50' textAlign='Center' isPrimaryKey={true} />
          <ColumnDirective field='patientId' headerText='Patient ID' width='100' textAlign='Center' />
          <ColumnDirective field='doctorId' headerText='Doctor ID' width='100' textAlign='Center' />
          <ColumnDirective field='type' headerText='Type' width='100' textAlign='Center' />
          <ColumnDirective field='startDate' headerText='Start' width='100' textAlign='Center' />
          <ColumnDirective field='endDate' headerText='End' width='100' textAlign='Center' />
          <ColumnDirective headerText='Commands' width='120' commands={commands} />
        </ColumnsDirective>
        <Inject services={[Page, Selection, Toolbar, Edit, Sort, Filter, CommandColumn]} />
      </GridComponent>
    </div>
  );
};
