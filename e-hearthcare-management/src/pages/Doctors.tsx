import React from 'react';
import { Header } from '../components/Header';
import { GridComponent, ColumnsDirective, ColumnDirective, Page, Selection,
  Inject, Edit, Toolbar, Sort, Filter, CommandColumn
} from '@syncfusion/ej2-react-grids';
import { DataManager, WebApiAdaptor } from '@syncfusion/ej2-data';
import { selectionsettings, toolbarOptions, editing, commands } from '../utils/DataBindingUtil';

export const Doctor = () => {
  const dataManager: DataManager = new DataManager({
    adaptor: new WebApiAdaptor(),
    url: "http://192.168.64.2:31799/ehealthcare/v1/doctors",
    //url: "http://localhost:8080/ehealthcare/v1/doctors",
  });

  return(
    <div className="m-2 md:m-10 mt-24 p-2 md:p-10 bg-white rounded-3xl">
      <Header category="Doctors" title="Doctors" />
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
          <ColumnDirective field='firstName' headerText='Firstname' width='100' textAlign='Center' /> 
          <ColumnDirective field='lastName' headerText='Lastname' width='100' textAlign='Center' />
          <ColumnDirective field='email' headerText='Email' width='100' textAlign='Center' />
          <ColumnDirective field='phoneNumber' headerText='Phone Number' width='100' textAlign='Center' />
         <ColumnDirective headerText='Commands' width='120' commands={commands} />
        </ColumnsDirective>
        <Inject services={[Page, Selection, Toolbar, Edit, Sort, Filter, CommandColumn]} />
      </GridComponent>
    </div>
  );
};
