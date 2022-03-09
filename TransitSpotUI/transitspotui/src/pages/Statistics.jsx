import * as React from "react";
import { ResponsiveBar } from "@nivo/bar";
import StatisticsService from "../services/StatisticsService";
import StationsService from "../services/StationsService";
import { useState, useEffect } from "react";
import UserService from "../services/UserService";

import "../style.css";


export default function Statistics() {
  const [stat, setStat] = useState([]);
  const [cities, setCities]= useState([]);

  UserService.checkToken();

  useEffect(() => {
    getStatistics();
    getLegend();
  },[]);

  const getStatistics = async () => {
    await StatisticsService.getSalesPerWeekDay().then((response) => {
      setStat(response.data);
    });
  };

  
  const getLegend = async () => {
    await StationsService.getStations().then((response) => {
      setCities(response.data.map(x => x.name));
    });
  };


  const styles = {
    fontFamily: "sans-serif",
    textAlign: "center",
  };


  const sorter = {
    // "sunday": 0, // << if sunday is first day of week
    "monday": 1,
    "tuesday": 2,
    "wednesday": 3,
    "thursday": 4,
    "friday": 5,
    "saturday": 6,
    "sunday": 7
  }

  


  return (
    <div style={styles}>
      <h1>Sales per week</h1>
      <div style={{ height: "600px", justifyContent:"center", display: "flex", alignItems:"center",  }}>
      <ResponsiveBar
        data={stat.sort(function sortByDay(a, b) {
          let day1 = a.day.toLowerCase();
          let day2 = b.day.toLowerCase();
          return sorter[day1] - sorter[day2];
        })}
        keys={cities}
        indexBy="day"
        margin={{ top: 40, right: 300, bottom: 40, left: 300 }}
        padding={0.3}
        valueScale={{ type: 'linear' }}
        indexScale={{ type: 'band', round: true }}
        colors={{ scheme: 'nivo' }}
        theme={{
          fontSize: 16,
         }}
        defs={[
            {
                id: 'dots',
                type: 'patternDots',
                background: 'inherit',
                color: '#38bcb2',
                size: 4,
                padding: 1,
                stagger: true
            },
            {
                id: 'lines',
                type: 'patternLines',
                background: 'inherit',
                color: '#eed312',
                rotation: -45,
                lineWidth: 6,
                spacing: 10
            }
        ]}
        fill={[
            {
                match: {
                    id: 'Groningen'
                },
                id: 'dots'
            },
            {
                match: {
                    id: 'sandwich'
                },
                id: 'lines'
            }
        ]}
        borderColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
        axisTop={null}
        axisRight={null}
        axisBottom={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: 'day',
            legendPosition: 'middle',
            legendOffset: 32
        }}
        axisLeft={{
            tickSize: 5,
            tickPadding: 5,
            tickRotation: 0,
            legend: 'sales',
            legendPosition: 'middle',
            legendOffset: -40
        }}
        labelSkipWidth={12}
        labelSkipHeight={12}
        labelTextColor={{ from: 'color', modifiers: [ [ 'darker', 1.6 ] ] }}
        legends={[
            {
                dataFrom: 'keys',
                anchor: 'bottom-right',
                direction: 'column',
                justify: false,
                translateX: 120,
                translateY: 0,
                itemsSpacing: 2,
                itemWidth: 100,
                itemHeight: 20,
                itemDirection: 'left-to-right',
                itemOpacity: 0.85,
                symbolSize: 20,
                effects: [
                    {
                        on: 'hover',
                        style: {
                            itemOpacity: 1
                        }
                    }
                ]
            }
        ]}
        role="application"
        ariaLabel="Nivo bar chart demo"
        barAriaLabel={function(e){return e.id+": "+e.formattedValue+" in country: "+e.indexValue}}
    />
      </div>
    </div>
  );
}