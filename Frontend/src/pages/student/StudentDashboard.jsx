import { useState, useEffect, useCallback } from 'react';
import { Card, Table, Tag, Alert, Statistic, Row, Col, message } from 'antd';
import { CheckCircleOutlined, CloseCircleOutlined, BookOutlined, TrophyOutlined } from '@ant-design/icons';
import { studentService } from '../../services/studentService';
import { useAuth } from '../../hooks/useAuth';
import { calculateAttendancePercentage, isEligibleForExam } from '../../utils/helpers';

const StudentDashboard = () => {
  const { userId } = useAuth();
  const [attendance, setAttendance] = useState([]);
  const [marks, setMarks] = useState([]);
  const [fetching, setFetching] = useState(false);

  const fetchData = useCallback(async () => {
    if (!userId) return;
    
    setFetching(true);
    try {
      const [attendanceResponse, marksResponse] = await Promise.all([
        studentService.getAttendance(userId),
        studentService.getMarks(userId),
      ]);
      setAttendance(attendanceResponse || []);
      setMarks(marksResponse || []);
    } catch (error) {
      message.error('Failed to fetch data');
      console.error('Error fetching data:', error);
    } finally {
      setFetching(false);
    }
  }, [userId]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const attendancePercentage = calculateAttendancePercentage(attendance);
  const eligibleForExam = isEligibleForExam(attendance);
  
  // Calculate average marks
  const averageMarks = marks.length > 0
    ? (marks.reduce((sum, mark) => sum + (mark.marks || 0), 0) / marks.length).toFixed(2)
    : 0;

  // Get unique subjects count from attendance and marks
  const attendanceSubjects = new Set(attendance.map(a => a.subjectId).filter(Boolean));
  const marksSubjects = new Set(marks.map(m => m.subjectId).filter(Boolean));
  const uniqueSubjects = new Set([...attendanceSubjects, ...marksSubjects]).size;
  const totalAttendanceRecords = attendance.length;
  const presentCount = attendance.filter(a => a.present).length;

  const attendanceColumns = [
    {
      title: 'Subject',
      dataIndex: 'subjectName',
      key: 'subject',
      width: '40%',
      align: 'center',
    },
    {
      title: 'Date',
      dataIndex: 'createdAt',
      key: 'date',
      width: '30%',
      align: 'center',
      render: (date) => new Date(date).toLocaleDateString(),
    },
    {
      title: 'Status',
      dataIndex: 'present',
      key: 'status',
      width: '30%',
      align: 'center',
      render: (present) => (
        <Tag color={present ? 'green' : 'red'}>
          {present ? 'Present' : 'Absent'}
        </Tag>
      ),
    },
  ];

  const marksColumns = [
    {
      title: 'Subject',
      dataIndex: 'subjectName',
      key: 'subject',
      width: '40%',
      align: 'center',
    },
    {
      title: 'Marks',
      dataIndex: 'marks',
      key: 'marks',
      width: '30%',
      align: 'center',
      render: (marks) => (
        <Tag color={marks >= 40 ? 'green' : marks >= 33 ? 'orange' : 'red'}>
          {marks}
        </Tag>
      ),
    },
    {
      title: 'Date',
      dataIndex: 'createdAt',
      key: 'date',
      width: '30%',
      align: 'center',
      render: (date) => new Date(date).toLocaleDateString(),
    },
  ];

  return (
    <div className="h-full flex flex-col justify-evenly" style={{ padding: '1rem' }}>
      <h1 className="text-3xl font-bold mb-6 text-center">Student Dashboard</h1>

      {!eligibleForExam && (
        <Alert
          message="Not Eligible for Exam"
          description={`Your attendance is ${attendancePercentage}%. You need at least 75% attendance to be eligible for exams.`}
          type="warning"
          showIcon
          className="mb-6"
        />
      )}

      <Row gutter={[16, 16]} className="mb-6">
        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Attendance"
              value={attendancePercentage}
              suffix="%"
              prefix={eligibleForExam ? <CheckCircleOutlined /> : <CloseCircleOutlined />}
              valueStyle={{ color: eligibleForExam ? '#3f8600' : '#cf1322' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Subjects"
              value={uniqueSubjects}
              prefix={<BookOutlined />}
              valueStyle={{ color: '#1890ff' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Average Marks"
              value={averageMarks}
              prefix={<TrophyOutlined />}
              valueStyle={{ color: '#722ed1' }}
            />
          </Card>
        </Col>
        <Col xs={24} sm={12} lg={6}>
          <Card>
            <Statistic
              title="Present Days"
              value={presentCount}
              suffix={`/ ${totalAttendanceRecords}`}
              valueStyle={{ color: '#3f8600' }}
            />
          </Card>
        </Col>
      </Row>

      <Row gutter={[16, 16]}>
        <Col xs={24} lg={12}>
          <Card title="My Attendance" className="h-full">
            <Table
              columns={attendanceColumns}
              dataSource={attendance}
              loading={fetching}
              rowKey="id"
              scroll={{ x: 'max-content' }}
            />
          </Card>
        </Col>
        <Col xs={24} lg={12}>
          <Card title="My Marks" className="h-full">
            <Table
              columns={marksColumns}
              dataSource={marks}
              loading={fetching}
              rowKey="id"
              scroll={{ x: 'max-content' }}
            />
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default StudentDashboard;
